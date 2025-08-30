package com.globalbooks.catalog;

import com.globalbooks.catalog.generated.GetBookDetailsResponse.Book;
import com.globalbooks.catalog.generated.CatalogServicePortType;
import javax.jws.WebService;

@WebService(endpointInterface = "com.globalbooks.catalog.generated.CatalogServicePortType")
public class CatalogServiceImpl implements CatalogServicePortType {

    @Override
    public Book getBookDetails(String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("The Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setPrice(25.99);
        return book;
    }
}
