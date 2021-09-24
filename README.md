# HackerNewsChecker とは

Wada Akira が Android エンジニアとして、スキルを向上させるために作成した学習用の Android アプリ。
また、Wada Akira のスキルチェックとして利用されることも想定している。

## アプリの動作要件

- Android 9.0 以上の実機/エミュレータでなければ動作しない
- デュアルディスプレイ等、複数のディスプレイを持つデバイスへのインストールは想定していない
- Pad、Android TV、ウェアラブル等、スマートフォーン以外のデバイスへのインストールは想定していない

## アプリの機能要件

- アプリを起動すると、Hacker News API と接続し、API 接続時のトップ記事を取得して、アプリトップページにタイトル、著者等を一覧形式で表示する
    - Hacker News : https://news.ycombinator.com/
    - Hacker News API : https://github.com/HackerNews/API
- タイトルをタップすると、外部ブラウザに該当ページが表示される
- 外部ブラウザで開いたページは Database に履歴として保存される
- ツールバーの三点リーダをタップして表示するポップアップメニューから画面の切り替えができる
- 履歴から外部ブラウザを開くことができる
- 履歴を削除することができる
- 使い方を一通り紹介するページを表示できる
    - 使い方がわからない場合、まずはこちらの画面を表示すること
- 当アプリの開発で利用しているライセンスを表示できる

## 機能要件を満たすために利用されている設計/技術

### 設計

- MVP アーキテクチャ + UseCase
    - Model としてユースケースを利用する
    - View は画面の表示と画面遷移を行う
    - Presenter は View と Model を結合し、View と Model の結合度を弱める
        - Model の再利用性を高める
    - Presenter を Interface として定義することで、Presenter を確認するだけで画面の仕様を把握できるようにする
    - View/Model も Interface として定義する
- Repository パターン
    - API or Database or SharedPreferences 等、データアクセスの場所を Presenter から隠蔽する
    - UseCase から呼び出される
- 以下の機能を独立したモジュールとして作成する
    - repository: データアクセス
    - dto: モジュール間のデータ転送用オブジェクトを定義する
    - usecase: プレゼンターが利用するユースケース
    - how_to: 使い方画面
    - view_common: 起動時画面、履歴画面共通の素材/ユーティリティ
    - history: 閲覧履歴画面
    - main: 起動時画面
    - app: 起動時アクティビティ/DI の制御
- 単体テスト
    - テスティングフレームワーク Spek2 とモックライブラリ MockK を導入する
        - Spek2 が最も実務経験が豊富なため
    - テストは、Repository をテスト対象とする
        - Retrofit もテスト対象
        - Room はテストのために context が必要になるため、テストを見送る
        - サンプルアプリのために Presenter までテスト対象にするのもいかがなものかと考えたため
- UI テストについては作成者にノウハウがないため、実装を見送る

### 利用している技術

- hilt-dagger2
    - Context, Presenter, UseCase, Retrofit, Database, Repository を DI するため
    - DI するオブジェクトは、すべてアプリケーションと同一の生存期間をもたせる
        - 実装を簡略化するため
- kotlin
    - 安全かつ効率的なコーディング（特に NPE 対策）のため
- coroutine
    - メインスレッドをブロックしない処理（通信/DB アクセス）を実装するため
- okhttp3
    - API 通信のため
- retrofit2
    - API 通信のため
- Moshi
    - JSON ハンドリングのため
- Room
    - DB アクセスのため
- ViewBinding
    - Kotlin Android Extensions が非推奨になったため

## 開発環境

- Mac Big Sur 11.6
- Android Studio Arctic Fox 2020.3.1 Patch 2 + kotlin 1.5.31

## アプリのライセンス

MIT ライセンスを採用している。
Wada Akira の名称を明記してもらえれば利用は自由だが、当アプリに関わるあらゆる不具合について、Wada Akira は一切の責任を負わない。
また、Android OS のバージョンアップ、Android OS の退廃、OSS の非推奨化等、当アプリのメンテナンスが必要な場合も、
Wada Akira はメンテナンスについて一切の責任を負わない。

最後に、当アプリは、作成者の目的を達成するか、作成者が不要と判断した時、事前連絡なしに公開を終了させることがある。