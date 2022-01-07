//プレビュー画像を追加する処理
function previewFile(file) {
  const preview = document.getElementById("preview");
  //以前に選択した画像を削除する処理
  if(preview.hasChildNodes()) {
	while(preview.firstChild) {
	  preview.removeChild(preview.firstChild);
	}
  }
  const reader = new FileReader();
  reader.onload = function (e) {
    const imageUrl = e.target.result;
    const img      = document.createElement("img");
    img.src = imageUrl;
    preview.appendChild(img);
  }
  reader.readAsDataURL(file);
}
(function() {
  const selectMax   = 3;
  const upSizeMax   = 3 * 1024 * 1024;
  let   fileList    = null;
  let   sumFileSize = 0;

  //<input>でファイルが選択されたときの処理、複数選択対応
  const fileInput = document.getElementById("example");
  const handleFileSelect = () => {
    fileList = fileInput.files;
    sumFileSize = 0;
    for (let i = 0; i < selectMax; i++) {
      previewFile(fileList[i]);
      sumFileSize += fileList[i].size;
    }
  }
  fileInput.addEventListener("change",handleFileSelect);

  //登録ボタンを押したときの処理
  const pictureSubmit = document.getElementById("PICTURE_SUBMIT");
  pictureSubmit.addEventListener("click", function(e) {
    if(fileList == null || fileList.length == 0) {
      alert("ファイルを選択してください。");
      e.preventDefault();
    } else if(fileList.length > selectMax) {
  	  alert("登録できるファイルは3つまでです。");
  	  e.preventDefault();
    } else if(sumFileSize > upSizeMax) {
      alert("登録できるファイルの合計容量は 3MB までです。");
      e.preventDefault();
    }
  },false)
} ());