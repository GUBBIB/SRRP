import { Link } from "react-router-dom";

export default function BoardListPage() {
  const dummy = [
    { id: 1, title: "첫 글" },
    { id: 2, title: "공지사항" },
  ];

  return (
    <div>
      <h1>Board</h1>
      <ul>
        {dummy.map((p) => (
          <li key={p.id}>
            <Link to={`/board/${p.id}`}>{p.title}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}