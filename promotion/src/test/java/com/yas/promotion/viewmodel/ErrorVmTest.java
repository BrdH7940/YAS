package com.yas.promotion.viewmodel;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.promotion.viewmodel.error.ErrorVm;
import java.util.List;
import org.junit.jupiter.api.Test;

class ErrorVmTest {

    @Test
    void constructor_threeArgs_createsWithEmptyFieldErrors() {
        ErrorVm errorVm = new ErrorVm("404", "Not Found", "Promotion not found");

        assertThat(errorVm.statusCode()).isEqualTo("404");
        assertThat(errorVm.title()).isEqualTo("Not Found");
        assertThat(errorVm.detail()).isEqualTo("Promotion not found");
        assertThat(errorVm.fieldErrors()).isEmpty();
    }

    @Test
    void constructor_fourArgs_createsWithFieldErrors() {
        List<String> fieldErrors = List.of("name: must not be blank", "slug: must not be blank");
        ErrorVm errorVm = new ErrorVm("400", "Bad Request", "Validation failed", fieldErrors);

        assertThat(errorVm.statusCode()).isEqualTo("400");
        assertThat(errorVm.title()).isEqualTo("Bad Request");
        assertThat(errorVm.detail()).isEqualTo("Validation failed");
        assertThat(errorVm.fieldErrors()).hasSize(2);
        assertThat(errorVm.fieldErrors()).containsExactly(
            "name: must not be blank", "slug: must not be blank");
    }
}
