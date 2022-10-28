import React, { useState } from 'react';
import TeamAPI from '../api/TeamAPI';
import '../CSS/Style_Login.css';
import logo from '../images/logo.png';

function Login() {
  // 키보드 입력
  const [inputId, setInputId] = useState("");
  const [inputPassword, setInputPassoword] = useState("");

  // 오류 메시지
  const [idMessage, setIdMessage] = useState("");
  const [pwMessage, setPwMessage] = useState("");

  // 유효성 검사
  const [isId, setIsId] = useState("");
  const [isPw, setIsPw] = useState("");

  const onChangId = (e) => {
    setInputId(e.target.value);
    if (e.target.value.length < 5 || e.target.value.length > 12) {
      setIdMessage("5자리 이상 12자리 미만으로 입력해 주세요.");
      setIsId(false);    
    } else {
      setIdMessage("올바른 형식 입니다.");
      setIsId(true);
    }
  }

  const onChangePw = (e) => {
    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/
    const passwordCurrent = e.target.value ;
    setInputPassoword(passwordCurrent);
    if (!passwordRegex.test(passwordCurrent)) {
      setPwMessage('숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!')
      setIsPw(false)
    } else {
      setPwMessage('안전한 비밀번호에요 : )')
      setIsPw(true);
    }        
  }

  const onClickLogin = async(e) => {
    e.preventDefault();
    console.log("로그인버튼 눌렀음");

    try {
      // 로그인을 위한 axios 호출
      const res = await TeamAPI.userLogin(inputId, inputPassword);
      console.log("호출 TRY: " + res.data.result);
      
      if(res.data.result === "OK") {
        window.localStorage.setItem("userId", inputId);
        window.localStorage.setItem("userPw", inputPassword);
        window.location.replace("/Home");
      } else {
        alert("아이디/비밀번호를 확인하시오!");
      }
    } catch (e) {
      alert("1111나는 팝업 창이다." + inputId + inputPassword);
      console.log("로그인 에러..");
    }
  }

  return(
      <div className="Login-card-container">
        
        <div className="Login-card">

          <div className="Login-card-logo">
            <img src={logo} alt="logo" />
          </div>

          <div className="Login-card-header">
            <h1>Sign In</h1>
            <div>Please login to use platform</div>
          </div>

          <form action="" className="Login-card-form">

            <div className="Form-item">
              <span className="Form-item-icon material-symbols-rounded">mail</span>
              <input type="text" placeholder="Enter Email" value={inputId} onChange={onChangId} required />
            </div>
            <div className="hint">
              {inputId.length > 0 && <span className={`message ${isId ? 'success' : 'error'}`}>{idMessage}</span>}
            </div>

            <div className="Form-item">
              <span className="Form-item-icon material-symbols-rounded">lock</span>
              <input type="password" placeholder="Enter Password" value ={inputPassword} onChange={onChangePw} required />
            </div>
            <div className="hint">
              {inputPassword.length > 0 && (
              <span className={`message ${isPw ? 'success' : 'error'}`}>{pwMessage}</span>)}
            </div>

            <div className="Form-item-other">
              <div className="Checkbox">
                <input type="checkbox" id="rememberMeCheckbox" />
                {/* -------- for -> htmlFor  카멜표기법으로 해야함!!!! */}
                <label htmlFor="rememberMeCheckbox">Remember me</label>
              </div>
              <a href="/">I forgot my password</a>
            </div>

            <button type="submit" onClick={onClickLogin}>Sign In</button>

          </form>

          <div className="Login-card-footer">
            Don't have an account? <a href="/signup">Create a free account</a>.
          </div>

        </div>
        <div className="Login-card-social">
          <div>Other Sign-in Platform</div>

          <div className="Login-card-social-btns">
                  {/* 10/26 -> stroke-line 등등등 카멜표기법으로 변경해야함. */}
            <a href="/">
              <svg xmlns="http://www.w3.org/2000/svg" className="icon icon-tabler icon-tabler-brand-facebook" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                <path d="M7 10v4h3v7h4v-7h3l1 -4h-4v-2a1 1 0 0 1 1 -1h3v-4h-3a5 5 0 0 0 -5 5v2h-3"></path>
              </svg>
            </a>

            <a href="/">
              <svg xmlns="http://www.w3.org/2000/svg" className="icon icon-tabler icon-tabler-brand-google" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                <path d="M17.788 5.108a9 9 0 1 0 3.212 6.892h-8"></path>
              </svg>
            </a>

          </div>
        </div>
      </div>
  );
}

export default Login;