const pagingTrigger = document.querySelectorAll('a[href^="jump-"]');
for(let i = 0; i < pagingTrigger.length; i++) {
  pagingTrigger[i].addEventListener('click', (e) => {
	e.preventDefault();
	let pageNum = pagingTrigger[i].innerText;
	//console.log(pageNum);
	const hiddenSelect = document.getElementById("SELECT_PAGE");
	//console.log(hiddenSelect.value);
	if(pageNum === "<<") {
	  pageNum = 1;
	} else if(pageNum === ">>") {
	  pageNum = 0;
	}
	hiddenSelect.value = pageNum;
	//console.log(hiddenSelect.value);
	document.PAGE_TRANSITION.submit();
  });
}