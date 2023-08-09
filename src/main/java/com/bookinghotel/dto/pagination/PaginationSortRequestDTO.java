package com.bookinghotel.dto.pagination;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationSortRequestDTO extends PaginationRequestDTO {

    @Parameter(description = "The name of property want to sort.")
    private String sortBy = CommonConstant.EMPTY_STRING;

    @Parameter(description = "Sorting criteria: ASC|DESC. Default sort order is descending.")
    @Pattern(regexp = "^(ASC)|(DESC)$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
    private String sortType = CommonConstant.SORT_TYPE_DESC;

    public String getSortBy(SortByDataConstant constant) {
        return constant.getSortBy(sortBy);
    }
}
