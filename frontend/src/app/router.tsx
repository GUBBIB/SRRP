import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../components/layout/RootLayout";

import HomePage from "../pages/Home";
import LoginPage from "../pages/Auth/LoginPage";
import BoardListPage from "../pages/Board/BoardListPage";
import BoardDetailPage from "../pages/Board/BoardDetailPage";
import BoardWritePage from "../pages/Board/BoardWritePage";
import NotFoundPage from "../pages/NotFound";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    errorElement: <NotFoundPage />, // 라우터 레벨 에러/없는 경로 대응
    children: [
      { index: true, element: <HomePage /> },          // "/"
      { path: "login", element: <LoginPage /> },       // "/login"
      { path: "board", element: <BoardListPage /> },   // "/board"
      { path: "board/write", element: <BoardWritePage /> }, // "/board/write"
      { path: "board/:id", element: <BoardDetailPage /> },  // "/board/:id"
      { path: "*", element: <NotFoundPage /> },        // 나머지
    ],
  },
]);

