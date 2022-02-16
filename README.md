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
  - Javascript
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
- Javascriptの動作
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
- Javascriptの動作
  - 画像プレビュー
    - 選択した画像の表示
    - 画像ファイルのチェック
    - 未選択やチェックが通らなかったときに画像登録を押したときのメッセージ表示
<br>

#### 管理者ログイン
![admin_login_transition](https://user-images.githubusercontent.com/91111453/154207697-b954c754-4985-41ff-85f7-4d4e1b5030da.jpg)
- Javascriptの動作
  - ログイン用入力のチェック
    - 未入力欄があるときのメッセージ表示
  - クリックしたページ番号取得
<br>

#### ユーザー削除
![delete_transition](https://user-images.githubusercontent.com/91111453/154207893-ee776fb3-ae34-48a1-9071-669f1270388e.jpg)
- Javascriptの動作
  - 選択削除
    - 選択削除実行前のメッセージ表示
    - 実行キャンセル時のチェックボックスの選択解除
  - 全件削除
    - 全件削除実行前のメッセージ表示（2回）
<br>
