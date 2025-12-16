
BEGIN;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
    CREATE TYPE user_role AS ENUM ('USER', 'ADMIN', 'MANAGER', 'SYSTEM');
  END IF;

  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'provider_type') THEN
    CREATE TYPE provider_type AS ENUM ('LOCAL', 'GOOGLE', 'KAKAO', 'NAVER');
  END IF;

  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'like_type') THEN
    CREATE TYPE like_type AS ENUM ('POST', 'COMMENT');
  END IF;

  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'notification_type') THEN
    CREATE TYPE notification_type AS ENUM ('COMMENT', 'FOLLOW', 'SYSTEM');
  END IF;
END $$;


-- users
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  nickname VARCHAR(255) NOT NULL UNIQUE,
  profile_image_url VARCHAR(255),

  role user_role NOT NULL DEFAULT 'USER',
  provider provider_type NOT NULL DEFAULT 'LOCAL',

  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- boards
CREATE TABLE IF NOT EXISTS boards (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- posts
CREATE TABLE IF NOT EXISTS posts (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  view_count BIGINT NOT NULL DEFAULT 0,

  user_id BIGINT NOT NULL,
  board_id BIGINT NOT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_posts_board FOREIGN KEY (board_id) REFERENCES boards(id)
);

-- comments
CREATE TABLE IF NOT EXISTS comments (
  id BIGSERIAL PRIMARY KEY,
  comment TEXT NOT NULL,

  user_id BIGINT NOT NULL,
  post_id BIGINT NOT NULL,
  parent_id BIGINT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id),
  CONSTRAINT fk_comments_parent FOREIGN KEY (parent_id) REFERENCES comments(id)
);

-- likes
CREATE TABLE IF NOT EXISTS likes (
  id BIGSERIAL PRIMARY KEY,
  type like_type NOT NULL,

  post_id BIGINT NULL,
  comment_id BIGINT NULL,
  user_id BIGINT NOT NULL,

  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts(id),
  CONSTRAINT fk_likes_comment FOREIGN KEY (comment_id) REFERENCES comments(id),
  CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id),

  CONSTRAINT ck_like_target CHECK (
    (post_id IS NOT NULL AND comment_id IS NULL AND type = 'POST'::like_type) OR
    (post_id IS NULL AND comment_id IS NOT NULL AND type = 'COMMENT'::like_type)
  )
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_likes_post_user
ON likes(post_id, user_id) WHERE post_id IS NOT NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_likes_comment_user
ON likes(comment_id, user_id) WHERE comment_id IS NOT NULL;

-- Notification
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,

    message VARCHAR(255) NOT NULL,
    target_url VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    type notification_type NOT NULL,

    receiver_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_notifications_receiver FOREIGN KEY (receiver_id) REFERENCES users(id),
    CONSTRAINT fk_notifications_sender FOREIGN KEY (sender_id) REFERENCES users(id)
);

TRUNCATE TABLE likes, comments, posts, boards, users
RESTART IDENTITY CASCADE;

-- password = 1234 (bcrypt)
INSERT INTO users (
  id, email, password, name, nickname, profile_image_url,
  role, provider,
  created_at, updated_at, deleted_at, is_deleted
) VALUES
    (1, 'system@srrp.com', '$2a$10$uhgliDhys84DdBOkIdHnser/PnukZankTcH3EzXOosr21f2hXXaqu', 'SYSTEM', 'SYSTEM', NULL,
 'SYSTEM', 'LOCAL', NOW(), NOW(), NULL, false),
(2, 'user1@srrp.com', '$2a$10$uhgliDhys84DdBOkIdHnser/PnukZankTcH3EzXOosr21f2hXXaqu', 'test1', 'nickTest1', NULL,
 'USER', 'LOCAL', NOW(), NOW(), NULL, false),
(3, 'user2@srrp.com', '$2a$10$uhgliDhys84DdBOkIdHnser/PnukZankTcH3EzXOosr21f2hXXaqu', 'test2', 'nickTest2', NULL,
 'USER', 'LOCAL', NOW(), NOW(), NULL, false);

INSERT INTO boards (
  id, name, description,
  created_at, updated_at, deleted_at, is_deleted
) VALUES
(1, '전체', '전체 게시판', NOW(), NOW(), NULL, false),
(2, '자유', '자유롭게 이야기', NOW(), NOW(), NULL, false),
(3, '공지', '운영 공지', NOW(), NOW(), NULL, false),
(4, '건의사항', '건의 사항 게시판', NOW(), NOW(), NULL, false);

INSERT INTO posts (
  id, title, content, view_count,
  user_id, board_id,
  created_at, updated_at, deleted_at, is_deleted
) VALUES
(1, '첫 글입니다', '안녕하세요. 테스트용 첫 게시글입니다.', 10, 2, 1, NOW(), NOW(), NULL, false),
(2, '자유게시판 테스트', '자유게시판 글 내용입니다.', 3, 2, 2, NOW(), NOW(), NULL, false),
(3, '두번째 유저 글', 'user2가 작성한 글입니다.', 1, 3, 1, NOW(), NOW(), NULL, false);

INSERT INTO comments (
  id, comment,
  user_id, post_id, parent_id,
  created_at, updated_at, deleted_at, is_deleted
) VALUES
(1, '첫 댓글!', 3, 1, NULL, NOW(), NOW(), NULL, false),
(2, '대댓글입니다.', 2, 1, 1, NOW(), NOW(), NULL, false),
(3, '자유게시판 댓글', 3, 2, NULL, NOW(), NOW(), NULL, false);

INSERT INTO likes (
  id, type,
  post_id, comment_id, user_id,
  created_at, updated_at, deleted_at, is_deleted
) VALUES
(1, 'POST', 1, NULL, 3, NOW(), NOW(), NULL, false),
(2, 'POST', 2, NULL, 3, NOW(), NOW(), NULL, false),
(3, 'COMMENT', NULL, 1, 2, NOW(), NOW(), NULL, false);


SELECT setval(pg_get_serial_sequence('users','id'), (SELECT MAX(id) FROM users));
SELECT setval(pg_get_serial_sequence('boards','id'), (SELECT MAX(id) FROM boards));
SELECT setval(pg_get_serial_sequence('posts','id'), (SELECT MAX(id) FROM posts));
SELECT setval(pg_get_serial_sequence('comments','id'), (SELECT MAX(id) FROM comments));
SELECT setval(pg_get_serial_sequence('likes','id'), (SELECT MAX(id) FROM likes));
SELECT setval(pg_get_serial_sequence('notifications','id'), (SELECT MAX(id) FROM notifications));


COMMIT;
