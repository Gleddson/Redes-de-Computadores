/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maranhomsecondaryserver.Controller;

import Model.BookModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static maranhonSecondaryServer.MaranhonSecondaryServer.books;

/**
 *
 * @author Gledson
 */
public class Controller {

    public static String[][] getBooks() throws IOException {
        String[][] dataBooks = new String[books.getDataBooks().size()][3];

        if (books.isCanChage()) {
            books.setCanChage(false);

            for (int i = 0; i < books.getDataBooks().size(); i++) {
                dataBooks[i][0] = ((BookModel) books.getDataBooks().get(i)).getName();
                dataBooks[i][1] = ((BookModel) books.getDataBooks().get(i)).getValue();
                dataBooks[i][2] = ((BookModel) books.getDataBooks().get(i)).getAmount();
            }
        } else {
            while(books.isCanChage() == false) {
                System.out.println("Waiting can change");
            }
            books.setCanChage(false);
            for (int i = 0; i < books.getDataBooks().size(); i++) {
                dataBooks[i][0] = ((BookModel) books.getDataBooks().get(i)).getName();
                dataBooks[i][1] = ((BookModel) books.getDataBooks().get(i)).getValue();
                dataBooks[i][2] = ((BookModel) books.getDataBooks().get(i)).getAmount();
            }
        }
        
        books.setCanChage(true);
        return dataBooks;
    }

    public static List AddBooks(String[][] books) {
        List<BookModel> dataBooks = new ArrayList();
        BookModel book = new BookModel();

        for (int i = 0; i < books.length; i++) {
            book.setName((String) books[i][0]);
            book.setValue((String) books[i][1]);
            book.setValue((String) books[i][2]);

            dataBooks.add(book);
        }

        return dataBooks;
    }
}
