package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
  private Scanner sc;
  private List<Article> articles;
  private String cmd;
  private String actionMethodName;

  public ArticleController(Scanner sc, List<Article> articles) {
    this.articles = articles;
    this.sc = sc;
  }

  @Override
  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;
    this.actionMethodName = actionMethodName;

    switch (actionMethodName) {
      case "write":
        doWrite();
        break;
      case "list":
        showList();
        break;
      case "detail":
        showDetail();
        break;
      case "delete":
        doDelete();
        break;
      case "modify":
        doModify();
        break;
    }
  }

  public void doWrite() {
    int id = articles.size() + 1;
    String regDate = Util.getNowDateStr();
    System.out.print("제목 : ");
    String title = sc.nextLine();
    System.out.print("내용 : ");
    String body = sc.nextLine();

    Article article = new Article(id, regDate, title, body);
    articles.add(article);
    System.out.printf("%d번 글이 생성되었습니다\n", id);
  }

  public void showList() {
    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다");
      return;
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
        return;
      }
    }

    System.out.println("번호  |     제목     |  조회");
    for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
      Article article = forPrintArticles.get(i);
      System.out.printf("%d     |    %s    |    %d\n", article.id, article.title, article.viewCnt);
    }
  }

  public void showDetail() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
      return;
    }
    foundArticle.increaseViewCnt();
    System.out.printf("번호 : %d\n", foundArticle.id);
    System.out.printf("날짜 : %s\n", foundArticle.regDate);
    System.out.printf("제목 : %s\n", foundArticle.title);
    System.out.printf("내용 : %s\n", foundArticle.body);
    System.out.printf("조회 : %d\n", foundArticle.viewCnt);
  }

  public void doDelete() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    int foundIdx = getArticleIndexById(id);

    if (foundIdx == -1) {
      System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
      return;
    }

    articles.remove(foundIdx);
    System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);
  }

  public void doModify() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
      return;
    }
    System.out.print("제목 : ");
    String title = sc.nextLine();
    System.out.print("내용 : ");
    String body = sc.nextLine();

    foundArticle.title = title;
    foundArticle.body = body;
    System.out.printf("%d번 게시물이 수정 되었습니다\n", id);
  }

  private int getArticleIndexById(int id) {
    int i = 0; // 인덱스 정보를 저장할 변수
    for (Article article : articles) {
      if (article.id == id) {
        return i;
      }
      i++;
    }

    return -1;
  }

  private Article getArticleById(int id) {
    int idx = getArticleIndexById(id);
    if (idx != -1) {
      return articles.get(idx);
    }
    return null;
  }
}
