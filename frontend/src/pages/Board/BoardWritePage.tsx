import { useNavigate } from "react-router-dom";

export default function BoardWritePage() {
  const navigate = useNavigate();

  return (
    <div>
      <h1>Write</h1>
      <p>여기는 글쓰기 폼 자리 (지금은 더미)</p>

      <button
        onClick={() => navigate("/board")}
        style={{ padding: "8px 12px", borderRadius: 8 }}
      >
        작성 완료(더미) → 목록으로
      </button>
    </div>
  );
}