function Login() {
  return (
    <>
      <h1>로그인</h1>
      <form>
        <label>
          <span>로그인 아이디</span>
        </label>
        <input type="text"></input>
        <br></br>
        <label>
          <span>로그인 패스워드</span>
        </label>
        <input type="password"></input>
        <br></br>
        <button type="submit">로그인</button>
      </form>
    </>
  );
}

export default Login;
