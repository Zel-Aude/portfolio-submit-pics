# portfolio-submit-pics
- ログインして画像データを投稿できるWebプロジェクトです。
- 投稿した画像データをユーザーのトップ画面に表示します。
- 外部フレームワークやライブラリを使わずに、標準APIのみで実装しました。
![ログイン前HOME画面](https://user-images.githubusercontent.com/91111453/154211001-4448b39a-13bb-4194-b061-0014dc44464c.jpg)

---
### 使用技術
- 開発環境 Eclipse 2019-12 (4.14.0)
  - Java11
  - Apache Tomcat v9.0
  - MySQL 5.7.32
  - JavaScript
  - HTML
  - CSS
  - XML

---
### 機能一覧
- ユーザー登録、ログイン機能
- 画像投稿機能
- 管理者によるユーザー削除機能
- 管理者ページでのページング処理機能
- スムーススクロール機能

---
### 画面遷移図
#### ユーザー登録及びログイン
![user_login_transition](https://user-images.githubusercontent.com/91111453/154207261-0978d151-9402-4548-b8cc-541327460d55.jpg)
- JavaScriptの動作
  - スムーススクロール
    - Itemのリンクをクリックして、各画像の頭にスクロールする
    - スクロール後に右下に出る「TOP」ボタンをクリックして、ページ最上部にスクロールする
  - 登録用入力のチェック
    - 未入力欄があるときのメッセージ表示
    - パスワードと確認欄の不一致メッセージ表示
  - ログイン用入力のチェック
    - 未入力欄があるときのメッセージ表示
<br>

#### 画像投稿
![picture_selection_transition](https://user-images.githubusercontent.com/91111453/154207548-9532fe35-bd3f-40c3-840b-64ad48f48920.jpg)
- JavaScriptの動作
  - 画像プレビュー
    - 選択した画像の表示
    - 画像ファイルのチェック
    - 未選択やチェックが通らなかったときに画像登録を押したときのメッセージ表示
<br>

#### 管理者ログイン
![admin_login_transition](https://user-images.githubusercontent.com/91111453/154207697-b954c754-4985-41ff-85f7-4d4e1b5030da.jpg)
- JavaScriptの動作
  - ログイン用入力のチェック
    - 未入力欄があるときのメッセージ表示
  - クリックしたページ番号取得
<br>

#### ユーザー削除
![delete_transition](https://user-images.githubusercontent.com/91111453/154207893-ee776fb3-ae34-48a1-9071-669f1270388e.jpg)
- JavaScriptの動作
  - 選択削除
    - 選択削除実行前のメッセージ表示
    - 実行キャンセル時のチェックボックスの選択解除
  - 全件削除
    - 全件削除実行前のメッセージ表示（2回）
<br>

---
### このプロジェクトを作成した理由
当初は、ヘッダー、スムーススクロール、ユーザー登録、管理者によるユーザー削除の機能を自ら実装することで、これらの機能を支えるプログラムやシステムに対する理解を深めることが目的でした。  
一通り実装してから、今度はユーザー毎にユーザーが任意の画像をアップロードして表示できる機能と、管理者画面でユーザーの一覧をページング処理をする機能を実装したいと思い、それらを追加しました。  
私は元々バックエンド側に興味があり、このプロジェクトを通じて目的の機能を実現するためにどのように考えてどのように組み込むかという、実装技術そのものへの理解を深めることができました。  
また、フロントエンド側に対しても、デザイナーの方が表現したい動きや表示を実現するための技術を習得したいと思い、スムーススクロールや画像プレビューといった機能にチャレンジしました。

---
### 苦労したところ
教わってもいないしどこかの教材で学習したものでもない、初めて取り組む内容ということで本当は全部苦労したと言いたいところですが、特にページング処理の実装が難しく感じました。  
そもそもどういう仕組みで動いているのか、DBからどのデータをどれだけ取得したらいいか、最後のページを指定したときのデータ取得はどうやるか、どうやってページ指定の値を取得して渡せばいいか等、それらを調べて理解して自分の中に落とし込むのに苦労しました。  
調べて何となくわかってきたら、いきなり全体を作り出すのではなく、データ取得部分の結果確認やページ指定値の取得確認等の小さなコードを書いて、それらが想定通りに動作しているかを確認し、少しずつ動作範囲を広げながら機能を組み立てていきました。  
コーディングをするに当たって自分が大事にしたいと思う考え方があります。  
それは、最低でも2つ以上のソースを調べ、そこに記載されていることをよく読んで理解に務め、自分の頭で考えて考えて納得できるまで考えて、自分の実現したいことに合うよう落とし込むことです。  
プログラムというのは誰が組んでも同じ動作をさせることができるので、コピペでも十分なものが作れることが多々ありますし、私もそうすることが多々あります。  
しかし、私はそれを好みません。結果としてコピペと大差が無かったとしても、十分に自分の頭で考えて納得しながら進んでいく姿勢が自力につながると信じています。

---
### こだわったところ
標準APIのみの実装にこだわりました。  
主にページング処理の実装方法について調べていると、フレームワークを用いての情報が多く出てきました。  
フレームワークを用いるのが一般的で、そちらの方がより楽に実装できるというのは理解しました。  
しかし、使わないで実装するにはどうしたらできるだろうと気になったのと、自分としてはより根幹の技術を先に身につけたいと思い、標準APIのみの実装をすることにしました。  
同様の理由でjQueryも使っていません。
