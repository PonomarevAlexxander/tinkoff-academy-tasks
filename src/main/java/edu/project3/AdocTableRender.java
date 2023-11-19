package edu.project3;

import java.util.List;

public class AdocTableRender implements TableRender {
    @Override
    @SuppressWarnings("MultipleStringLiterals")
    public String render(String header, List<List<String>> rowsData) {
        StringBuilder builder = new StringBuilder();
        builder.append(".Table ");
        builder.append(header);
        builder.append('\n');
        builder.append("|===\n");
        for (int row = 0; row < rowsData.size(); row++) {
            for (String column : rowsData.get(row)) {
                builder.append("|");
                builder.append(column);
                if (row == 0) {
                    builder.append(" ");
                } else {
                    builder.append("\n");
                }
            }
            builder.append("\n");
        }
        builder.append("|===\n");
        return builder.toString();
    }
}
