const topBtnDisplaySwitch = (elment) => {
  const scrollValue  = window.pageYOffset;
  const displayValue = 120;
  if(scrollValue > displayValue) {
    elment.classList.add("is-scroll");
  } else {
    if(elment.classList.contains("is-scroll")) {
      elment.classList.remove("is-scroll");
    }
  }
};

const pageTopBtn = document.getElementById("js-scroll-top");
window.addEventListener("scroll", () => {
  topBtnDisplaySwitch(pageTopBtn);
});
pageTopBtn.addEventListener("click", () => {
  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
});