package com.KoreaIT.java.AM;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.Controller;
import com.KoreaIT.java.AM.controller.MemberController;

import java.util.Scanner;

public class App {
  public void start() {
    System.out.println("== 프로그램 시작 ==");
    Scanner sc = new Scanner(System.in);
    MemberController memberController = new MemberController(sc);
    ArticleController articleController = new ArticleController(sc);

    articleController.makeTestData();
    memberController.makeTestData();

    while (true) {
      System.out.print("명령어 ) ");
      String cmd = sc.nextLine().trim();
      if (cmd.length() == 0) {
        System.out.println("명령어를 입력하세요");
        continue;
      }

      if (cmd.equals("system exit")) {
        break;
      }

      String[] cmdBits = cmd.split(" ");  // cmd == "article write"
      if (cmdBits.length == 1) {
        System.out.println("존재하지 않는 명령어 입니다.");
        continue;
      }

      String controllerName = cmdBits[0];   // article
      String actionMethodName = cmdBits[1]; // write

      Controller controller = null;
      if (controllerName.equals("article")) {
        controller = articleController;
      } else if (controllerName.equals("member")) {
        controller = memberController;
      } else {
        System.out.println("존재하지 않는 명령어 입니다.");
        continue;
      }

      String actionName = controllerName + "/" + actionMethodName;
      // 로그인이 필요한 작업을 시도한 경우
      switch (actionName) {
        case "article/write":
        case "article/delete":
        case "article/modify":
        case "member/logout":
          if (Controller.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            continue;
          }
          break;
      }

      // 로그아웃이 필요한 작업을 시도한 경우
      switch (actionName) {
        case "member/login":
        case "member/join":
          if (Controller.isLogined()) {
            System.out.println("로그아웃 후 이용하세요");
            continue;
          }
          break;
      }

      controller.doAction(cmd, actionMethodName);
    }

    System.out.println("== 프로그램 종료 ==");
    sc.close();
  }
}

