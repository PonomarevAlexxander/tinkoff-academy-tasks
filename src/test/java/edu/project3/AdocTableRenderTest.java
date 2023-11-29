package edu.project3;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AdocTableRenderTest {

    @Test
    void test_render() {
        List<List<String>> metrics = List.of(
            List.of("Metric", "Value"),
            List.of("files", "`access.log`"),
            List.of("date", "31.08.2023")
        );
        TableRender render = new AdocTableRender();
        assertThat(render.render("Common info", metrics))
            .isEqualTo(".Table Common info\n" +
                "|===\n" +
                "|Metric |Value \n" +
                "|files\n" +
                "|`access.log`\n" +
                "\n" +
                "|date\n" +
                "|31.08.2023\n" +
                "\n" +
                "|===\n");
    }
}
