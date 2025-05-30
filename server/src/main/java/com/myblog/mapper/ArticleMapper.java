package com.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myblog.entity.Article;
import com.myblog.entity.Category;
import com.myblog.vo.ArticleVO;
import com.myblog.vo.CategoryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    /**
     *  根据筛选条件获取文章
     * @param categoryId
     * @param tagId
     * @param publishTime
     * @param sortField
     * @param sortOrder
     * @return
     */
    List<ArticleVO> getArticlesByFilterAndSort(Long categoryId, Long tagId, String publishTime, String sortField, String sortOrder);

    @Delete("delete from article where id = #{id} and user_id = #{userId}")
    void deleteByIdAndUserId(Long id, Long userId);

    @Select("select * from article where status = 0")
    List<Article> getDraftArticles();

    void moveToRecycleBin(Long id, Long userId);

    void restoreFromRecycleBin(Long id, Long userId);

    void permanentlyDelete(Long id, Long userId);

    List<Article> getArticlesInRecycleBin(Long userId);

    void cleanExpiredRecycleBinArticles();

    @Update("update article set views = #{views} where id = #{articleId}")
    void updateArticleViews(Long articleId, Long views);


    @Update("update article set likes = #{likes} where id = #{articleId}")
    void updateArticleLikes(Long articleId, Long likes);

    void insertCategory(Category category);


    List<CategoryVO> getSubCategories(Long parentId);

    @Update("update article set status = #{i} where id = #{id}")
    void reviewArticle(Long id, int i);
}
