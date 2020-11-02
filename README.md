# HackerNewsChecker とは
作成者の Wada Akira が Android エンジニアとして、スキルを向上させるために作成した学習用の Android アプリ。
また、Wada Akira のスキルチェック用のアプリとして利用されることも想定している。

## アプリの動作要件

- Android 8.0 以上の実機/エミュレータでなければ動作しない
- デュアルディスプレイ等、複数のディスプレイを持つデバイスへのインストールは想定していない
- Pad、Android TV、ウェアラブル等、スマートフォーン以外のデバイスへのインストールは想定していない

## アプリの機能要件

- アプリを起動すると、Hacker News API と接続し、API 接続時のトップ記事を取得して、アプリトップページにタイトルと著者を一覧形式で表示する
    - Hacker News : https://news.ycombinator.com/
    - Hacker News API : https://github.com/HackerNews/API
- 表示されているタイトルをタップすると、WEBView に該当ページが表示される
    - Hacker News 以外のドメインには接続できないように制御する
- WEBView に開いたページは Database に履歴として保存される
- トップページと履歴画面はツールバーで切り替えることができる
- 履歴から WEBView を開くことができる
- 履歴を削除することができる

## 機能要件を満たすために利用されている設計/技術

### 設計

- MVP アーキテクチャ + UseCase
    - Model のモデルとしてユースケースを利用する
    - View は画面の表示と画面遷移を行う
    - Presenter は View と Model を結合し、View と Model の結合度を弱める
        - Model の再利用性を高める
    - Presenter を Interface として定義することで、Presenter を確認するだけで画面の仕様を把握できるようにする
    - View/Model(UseCase) も Interface として定義する
- Repository パターン
    - API or Database or SharedPreferences 等、データアクセスの場所を Presenter から隠蔽する
    - UseCase から呼び出される
- 単体テスト
    - テスティングフレームワーク Spek2 とモックライブラリ MockK を導入する
        - Spek2 が最も実務経験が豊富なため
    - テストは、Repository をテスト対象とする
        - サンプルアプリのために Presenter までテスト対象にするのもいかがなものかと考えたため
- UI テストについては作成者にノウハウがないため、実装を見送る

### 利用している技術

- dagger2
    - Context, Presenter, UseCase を DI するため
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

- Mac Catalina 10.15.7
- Android Studio 4.1 + kotlin 1.3.72

## アプリのライセンス

MIT ライセンスを採用している。
Wada Akira の名称を明記してもらえれば利用は自由だが、当アプリに関わる不具合について、Wada Akira は一切の責任を負わない。
また、Android OS のバージョンアップ、Android OS の退廃、OSS の非推奨化等、当アプリのメンテナンスが必要な場合も、
Wada Akira はメンテナンスについて一切の責任を負わない。

最後に、当アプリは、作成者の目的を達成するか、作成者が不要と判断した時、事前連絡なしに公開を終了させることがある。