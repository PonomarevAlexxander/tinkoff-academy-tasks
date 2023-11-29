package edu.project3;

import java.util.Arrays;
import java.util.List;

public class MarkdownTableRender implements TableRender {
    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public String render(String header, List<List<String>> rowsData) {
        StringBuilder builder = new StringBuilder();
        builder.append("#### ");
        builder.append(header);
        builder.append('\n');
        for (int row = 0; row < rowsData.size(); row++) {
            builder.append("| ");
            builder.append(String.join(" | ", rowsData.get(row)));
            builder.append(" |\n");
            if (row == 0) {
                builder.append("| ");
                String[] symbols = new String[rowsData.get(0).size()];
                Arrays.fill(symbols, "-");
                builder.append(String.join(" | ", symbols));
                builder.append(" |\n");
            }
        }
        return builder.toString();
    }
}
