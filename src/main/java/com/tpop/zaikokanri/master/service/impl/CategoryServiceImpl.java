package com.tpop.zaikokanri.master.service.impl;

import com.tpop.zaikokanri.components.ApiResponse;
import com.tpop.zaikokanri.constants.FieldConstant;
import com.tpop.zaikokanri.constants.MessageCode;
import com.tpop.zaikokanri.constants.ResponseStatusConst;
import com.tpop.zaikokanri.exceptions.APIErrorDetail;
import com.tpop.zaikokanri.exceptions.CommonException;
import com.tpop.zaikokanri.master.dto.CategoryDto;
import com.tpop.zaikokanri.master.dto.ICategoryDto;
import com.tpop.zaikokanri.master.entities.Category;
import com.tpop.zaikokanri.master.repository.CategoryRepository;
import com.tpop.zaikokanri.master.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MessageSource messageSource;

    /**
     *
     * @param categoryDto
     * @param page
     * @param limit
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    public ApiResponse<Object> getCategoryPage(CategoryDto categoryDto, Integer page, Integer limit, String lang) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        Locale locale = Locale.forLanguageTag(lang);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ICategoryDto> categoryPage = categoryRepository.getCategory(categoryDto, pageable);
        if (categoryPage.getTotalElements() == 0) {
            response.setMessage(
                    messageSource.getMessage(
                            MessageCode.DATA_NOT_FOUND, null, locale
                    )
            );
        } else {
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(categoryPage);
        return response;
    }

    /**
     *
     * @param categoryId
     * @param lang
     * @return
     */
    @Override
    public ApiResponse<Object> getCategoryById(Integer categoryId, String lang) {
        ApiResponse<Object> result = new ApiResponse<>();
        if (!Objects.isNull(categoryId)) {
            Locale locale = Locale.forLanguageTag(lang);
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty()) {
                result.setMessage(messageSource.getMessage(
                        MessageCode.DATA_NOT_FOUND, null, locale
                ));
            } else {
                result.setMessage(null);
            }
            result.setStatus(ResponseStatusConst.SUCCESS);
            result.setData(optionalCategory);
        }
        return result;
    }

    /**
     *
     * @param categoryList
     * @param lang
     * @return
     * @throws CommonException
     */
    @Override
    public List<Category> createCategory(List<Category> categoryList, String lang) throws CommonException {
        List<Category> createdCategory = new ArrayList<>();
        try {
            LocalDateTime current = LocalDateTime.now();
            Locale locale = Locale.forLanguageTag(lang);
            if (!CollectionUtils.isEmpty(categoryList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                categoryList.forEach(c -> {
                    if (c.getCategoryCd().isBlank()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.CATEGORY_CD,
                                MessageCode.NOT_BLANK,
                                messageSource.getMessage(
                                        MessageCode.NOT_BLANK, new Object[]{
                                                messageSource.getMessage(FieldConstant.CATEGORY_CD, null, locale)
                                        }, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }

                    if (Boolean.TRUE.equals(getCategoryByCategoryCode(c.getCategoryCd()))) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.intValue(),
                                FieldConstant.CATEGORY_CD,
                                MessageCode.DATA_ALREADY_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.DATA_ALREADY_EXISTS, new Object[]{c.getCategoryCd()}, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                });

                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.DATA_ALREADY_EXISTS)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                List<Category> list = new ArrayList<>();
                for (Category c : categoryList) {
                    Category category = Category.builder()
                            .id(c.getId())
                            .categoryCd(c.getCategoryCd())
                            .majorCategory(c.getMajorCategory())
                            .mediumCategory(c.getMediumCategory())
                            .subCategory(c.getSubCategory())
                            .createdAt(current)
                            .createdBy("user")
                            .updatedAt(current)
                            .updatedBy("user")
                            .build();
                    list.add(category);
                }
                createdCategory = categoryRepository.saveAllAndFlush(list);
            }

        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdCategory;
    }

    /**
     * @param categoryCode
     * @return
     */
    @Override
    public Boolean getCategoryByCategoryCode(String categoryCode) {
        Optional<Category> category = categoryRepository.findCategoryByCategoryCd(categoryCode);
        return category.isPresent();
    }
}
