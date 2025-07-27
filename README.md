# Schedule Sample Application

## 概要 (Overview)

このプロジェクトは、Spring Bootを使用した分散スケジュールタスクのサンプルアプリケーションです。ShedLockライブラリを利用して、複数のアプリケーションインスタンスが同時に実行される環境でも、スケジュールタスクが一度だけ実行されることを保証します。

This project is a sample application demonstrating distributed scheduled tasks using Spring Boot. It utilizes the ShedLock library to ensure that scheduled tasks are executed only once, even in environments where multiple application instances are running simultaneously.

## 技術スタック (Technology Stack)

- Java 21 (Virtual Threads対応)
- Spring Boot 3.4.8
- PostgreSQL (データベース)
- ShedLock 6.9.2 (分散ロック)
- Docker & Docker Compose (開発環境)
- MyBatis (データベースアクセス)
- Lombok (ボイラープレートコード削減)

## 機能 (Features)

- 異なる実行間隔と異なるロック期間を持つ複数のスケジュールタスク
- PostgreSQLを使用した分散ロックの実装
- Docker Composeによる簡単なセットアップと実行
- Virtual Threadsを活用した効率的なスケジューリング

## プロジェクト構造 (Project Structure)

- `src/main/java/net/ksasaki/sample/schedule/ScheduleApplication.java`: メインアプリケーションとスケジュールタスクの実装
- `postgres/init/schema.sql`: ShedLock用のデータベーススキーマ
- `src/main/resources/application.yml`: アプリケーション設定
- `compose.yml`: Docker Compose設定
- `build.gradle`: ビルド設定とプロジェクト依存関係

## 実行方法 (How to Run)

### 前提条件 (Prerequisites)

- Docker と Docker Compose
- Java 21
- Gradle

### 手順 (Steps)

1. リポジトリをクローン:
   ```
   git clone <repository-url>
   cd schedule
   ```

2. Docker Composeでデータベースを起動:
   ```
   docker-compose up -d
   ```

3. アプリケーションを実行:
   ```
   ./gradlew bootRun
   ```

4. ログを確認して、スケジュールタスクが実行されていることを確認します。

## スケジュールタスクの説明 (Scheduled Tasks Description)

アプリケーションには4つのスケジュールタスクがあります:

1. `runA`: 1秒ごとに実行され、最低3秒間ロックされます
2. `runB`: 1秒ごとに実行され、最低4秒間ロックされます
3. `runC`: 0.5秒ごとに実行され、最低5秒間ロックされます
4. `runD`: 1秒ごとに実行され、最低2秒間ロックされます

これらのタスクは、異なるロック期間と実行間隔を持ち、ShedLockの動作を示すために設計されています。

## ShedLockについて (About ShedLock)

ShedLockは、分散環境でスケジュールタスクを実行するためのJavaライブラリです。複数のノードで同じタスクが同時に実行されるのを防ぎます。このサンプルアプリケーションでは、PostgreSQLをロック情報の保存に使用しています。

詳細については、[ShedLockのGitHubリポジトリ](https://github.com/lukas-krecan/ShedLock)を参照してください。

## ライセンス (License)

このプロジェクトは[MITライセンス](LICENSE)の下で公開されています。
