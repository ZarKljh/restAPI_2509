import { Link } from "react-router-dom";

function Nav() {
  return (
    <>
      <ul>
        <li>
          <Link to="/">HOME</Link>
        </li>
        <li>
          <Link to="/auth/login">LOGIN</Link>
        </li>
        <li>
          <Link to="/article/list">ArticleList</Link>
        </li>
      </ul>
    </>
  );
}

export default Nav;
