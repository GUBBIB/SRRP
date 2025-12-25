import { Link, useRouteError } from "react-router-dom";

export default function NotFoundPage() {
  const err = useRouteError();

  return (
    <div>
      <h1>404 / Not Found</h1>
      <p>요청한 페이지를 찾을 수 없습니다.</p>

      {/* 디버깅용: 라우터 에러가 있으면 확인 */}
      {err ? (
        <pre style={{ background: "#f6f6f6", padding: 12, borderRadius: 8, overflow: "auto" }}>
          {JSON.stringify(err, null, 2)}
        </pre>
      ) : null}

      <Link to="/">홈으로</Link>
    </div>
  );
}