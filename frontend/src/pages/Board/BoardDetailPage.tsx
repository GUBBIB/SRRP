import { useParams, Link } from "react-router-dom";

export default function BoardDetailPage() {
  const { id } = useParams();

  return (
    <div>
      <h1>Board Detail</h1>
      <p>post id: {id}</p>
      <Link to="/board">← Back to list</Link>
    </div>
  );
}