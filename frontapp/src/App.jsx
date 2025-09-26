import { BrowserRouter, Routes, Route } from "react-router-dom";
import Main from "./pages/Main";
import Login from "./pages/login";
import ArticleList from "./pages/list";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route index element={<Main />}></Route>
          <Route path="/auth/login" element={<Login />}></Route>
          <Route path="/article/list" element={<ArticleList />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
