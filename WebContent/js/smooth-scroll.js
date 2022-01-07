const smoothScrollTrigger = document.querySelectorAll('a[href^="#"]');
for(let i = 0; i < smoothScrollTrigger.length; i++) {
  smoothScrollTrigger[i].addEventListener('click', (e) => {
    e.preventDefault();
    let href = smoothScrollTrigger[i].getAttribute('href');
    //console.log(href) href要素の取得確認用
    let targetElement = document.getElementById(href.replace('#', ''));
    //console.log(targetElement) replace後のhref要素の取得確認用
    const rect = targetElement.getBoundingClientRect().top;
    const offset = window.pageYOffset;
    const gap = 60;
    const target = rect + offset - gap;
    window.scrollTo({
      top: target,
      behavior: 'smooth',
    });
  });
}