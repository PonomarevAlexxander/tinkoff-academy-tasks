package edu.project3;

import java.util.List;

public interface TableRender {
    String render(String header, List<List<String>> rowsData);
}
