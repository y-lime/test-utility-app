package com.example.testutilityapp.properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
class SamplePropertiesTest {

    @Autowired
    SampleProperties sampleProperties;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("myproperties.override", () -> "override");
    }

    @Test
    void 通常のプロパティ() throws Exception {
        // src/main/test/resources/custom.propertiesの設定値
        Assertions.assertThat(this.sampleProperties.getMypropertiesMain()).isEqualTo("init_main");
    }

    @Test
    void テストプロファイルで上書きしたプロパティ() throws Exception {
        // src/main/test/resources/custom-test.propertiesの設定値
        Assertions.assertThat(this.sampleProperties.getMypropertiesTest()).isEqualTo("test");
    }

    @Test
    void 空設定されたプロパティ() throws Exception {
        // src/main/test/resources/custom.propertiesの設定値
        Assertions.assertThat(this.sampleProperties.getMypropertiesEmpty()).isEmpty();
    }

    @Test
    void 未設定のプロパティ() throws Exception {
        // propertiesにて未設定のためSamplePropertiesクラスの@Valueのデフォルト値
        Assertions.assertThat(this.sampleProperties.getMypropertiesUndefined()).isEqualTo("default");
    }

    @Test
    void テストクラスで上書きしたプロパティ() throws Exception {
        // @DynamicPropertySourceで上書きした設定値
        Assertions.assertThat(this.sampleProperties.getMypropertiesOverride()).isEqualTo("override");
    }

    @Test
    void テストで除去プロパティ() throws Exception {
        // src/main/test/resources/custom.propertiesにて除去したためSamplePropertiesクラスの@Valueのデフォルト値
        Assertions.assertThat(this.sampleProperties.getMypropertiesRemove()).isEqualTo("default");
    }
}
