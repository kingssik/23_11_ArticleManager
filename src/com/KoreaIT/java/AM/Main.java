package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static List<Article> articles;

  static {
    articles = new ArrayList<>();
  }

  public static void main(String[] args) {
    System.out.println("== 프로그램 시작 ==");
    Scanner sc = new Scanner(System.in);
    makeTestData();

    while (true) {
      System.out.print("명령어 ) ");
      String cmd = sc.nextLine().trim();
      if (cmd.length() == 0) {
        continue;
      }

      if (cmd.equals("system exit")) {
        break;
      }

      if (cmd.equals("article write")) {
        int id = articles.size() + 1;
        String regDate = Util.getNowDateStr();
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, title, body);
        articles.add(article);
        System.out.printf("%d번 글이 생성되었습니다\n", id);

      } else if (cmd.startsWith("article list")) {
        if (articles.size() == 0) {
          System.out.println("게시글이 없습니다");
          continue;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();
        System.out.printf("검색어 : %s\n", searchKeyword);
        List<Article> forPrintArticles = articles;

        // 검색어가 존재하는 경우
        if (searchKeyword.length() > 0) {
          forPrintArticles = new ArrayList<>();

          for (Article article : articles) {
            if (article.title.contains(searchKeyword)) {
              forPrintArticles.add(article);
            }
          }

          // 검색어는 있지만 검색 결과가 없는 경우
          if (forPrintArticles.size() == 0) {
            System.out.println("검색 결과가 없습니다");
            continue;
          }
        }

        System.out.println("번호  |     제목     |  조회");
        for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
          Article article = forPrintArticles.get(i);
          System.out.printf("%d     |    %s    |    %d\n", article.id, article.title, article.viewCnt);
        }
      } else if (cmd.startsWith("article delete ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundIdx = -1;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundIdx = i;
            break;
          }
        }

        if (foundIdx == -1) {
          System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
          continue;
        }

        articles.remove(foundIdx);
        System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);

      } else if (cmd.startsWith("article detail ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = null;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundArticle = article;
            break;
          }
        }

        if (foundArticle == null) {
          System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
          continue;
        }
        foundArticle.increaseViewCnt();
        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.viewCnt);

      } else if (cmd.startsWith("article modify ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = null;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundArticle = article;
            break;
          }
        }

        if (foundArticle == null) {
          System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
          continue;
        }
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        foundArticle.title = title;
        foundArticle.body = body;
        System.out.printf("%d번 게시물이 수정 되었습니다\n", id);

      } else {
        System.out.println("존재하지 않는 명령어입니다");
      }
    }

    System.out.println("== 프로그램 종료 ==");
    sc.close();
  }

  private static void makeTestData() {
    System.out.println("게시물 테스트데이터를 생성합니다");
    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }
}

class Article {
  int id;
  String regDate;
  String title;
  String body;
  int viewCnt;

  public Article(int id, String regDate, String title, String body) {
    this(id, regDate, title, body, 0);
  }

  public Article(int id, String regDate, String title, String body, int viewCnt) {
    this.id = id;
    this.regDate = regDate;
    this.title = title;
    this.body = body;
    this.viewCnt = viewCnt;
  }

  public void increaseViewCnt() {
    viewCnt++;
  }
}
