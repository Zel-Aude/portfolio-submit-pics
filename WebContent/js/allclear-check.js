const elmClearSubmit = document.getElementById("ALL_CLEAR_SUBMIT");
elmClearSubmit.addEventListener("click", function(e) {
  let canSubmit = false;
  const firstDelMsg = "全件削除を実行しますか？";
  canSubmit = window.confirm(firstDelMsg);
  if(canSubmit) {
	const secondDelMsg = "本当に実行しますか？";
	canSubmit = window.confirm(secondDelMsg);
	if(canSubmit) {
	  ;
	} else {
	  e.preventDefault();
	}
  } else {
	e.preventDefault();
  }
},false)
