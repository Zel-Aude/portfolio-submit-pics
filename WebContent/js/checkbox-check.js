const elmDeleteSubmit = document.getElementById("DELETE_SUBMIT");
elmDeleteSubmit.addEventListener("click", function(e) {
  let delSelect = false;
  let canSubmit = false;
  const targets = document.DELETE_CHECK.DELETE_TARGETS.length;
  const delMsg  = "選択削除を実行しますか？";
  if(typeof targets === 'undefined') {
	//ユーザーが一人だけの場合のチェック確認
	if(document.DELETE_CHECK.DELETE_TARGETS.checked) {
	  delSelect = true;
	  canSubmit = window.confirm(delMsg);
	  if(!canSubmit) {
		document.DELETE_CHECK.DELETE_TARGETS.checked = false;
		e.preventDefault();
	  }
	}
  } else {
	//ユーザーが複数の場合のチェック確認
	let checkedTargets = new Array();
	for(let i = 0; i < targets; i++) {
	  if(document.DELETE_CHECK.DELETE_TARGETS[i].checked) {
		delSelect = true;
		checkedTargets[i] = document.DELETE_CHECK.DELETE_TARGETS[i];
      }
    }
	if(delSelect) {
	  canSubmit = window.confirm(delMsg);
	  if(!canSubmit) {
		let checkedTrues = checkedTargets.filter(Boolean);//空要素を削除
		for(let j = 0; j < checkedTrues.length; j++) {
	      checkedTrues[j].checked = false;
		}
		e.preventDefault();
	  }
	}
  }
  if(delSelect || canSubmit) {
	;
  } else {
	alert("項目が未選択です。");
	e.preventDefault();
  }
},false)
