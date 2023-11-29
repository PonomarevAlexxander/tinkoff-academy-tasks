package edu.project3;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MarkdownTableRenderTest {
    @Test
    void test_render() {
        List<List<String>> metrics = List.of(
            List.of("Metric", "Value"),
            List.of("files", "`access.log`"),
            List.of("date", "31.08.2023")
        );
        TableRender render = new MarkdownTableRender();
        assertThat(render.render("Common info", metrics))
            .isEqualTo("#### Common info\n" +
                "| Metric | Value |\n" +
                "| - | - |\n" +
                "| files | `access.log` |\n" +
                "| date | 31.08.2023 |\n");
    }
}
