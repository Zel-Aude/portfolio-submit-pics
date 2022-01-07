const elmSubmit = document.getElementById("USER_SUBMIT");
elmSubmit.onclick = function() {
  const elmUserId    = document.getElementById("ID_USER_ID");
  const elmPassword  = document.getElementById("ID_USER_PASS");
  const elmCheckPass = document.getElementById("ID_CHECK_PASS");
  let canSubmit = true;
  if(elmUserId.value == "" || elmPassword.value == "" || elmCheckPass.value == "") {
    alert("入力漏れの項目があります。");
    canSubmit = false;
  } else if(elmPassword.value != elmCheckPass.value) {
    alert("確認用パスワードが一致しません。");
    canSubmit = false;
  }
  return canSubmit;
}
