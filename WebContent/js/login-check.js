function loginCheck(id, pass) {
  let inputCheck = false;
  if(id.value == "" || pass.value == "") {
    inputCheck = true;
  }
  return inputCheck;
}
//管理者側のログインチェック
const elmAdminSubmit = document.getElementById("ADMIN_SUBMIT");
elmAdminSubmit.addEventListener("click", function(e) {
  const elmId       = document.getElementById("ID_ADMIN_ID");
  const elmPassword = document.getElementById("ID_ADMIN_PASS");
  if(loginCheck(elmId, elmPassword)) {
    alert("管理者側に入力漏れの項目があります。");
    e.preventDefault();
  }
},false)
//ユーザー側のログインチェック
const elmUserSubmit = document.getElementById("USER_SUBMIT");
elmUserSubmit.addEventListener("click", function(e) {
  const elmId       = document.getElementById("ID_USER_ID");
  const elmPassword = document.getElementById("ID_USER_PASS");
  if(loginCheck(elmId, elmPassword)) {
    alert("ユーザー側に入力漏れの項目があります。");
    e.preventDefault();
  }
},false)