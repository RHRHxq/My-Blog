package com.myblog.recommend;

import com.myblog.entity.Article;
import com.myblog.entity.ArticleFeatures;
import com.myblog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//创建推荐器类
@Component
public class ContentBasedArticleRecommender {
    @Autowired
    private ArticleMapper articleMapper;

    private ArticleSimilarityCalculator similarityCalculator = new ArticleSimilarityCalculator();

    public List<Article> recommendArticles(Long articleId, int numRecommendations) {
        List<Article> allArticles = articleMapper.selectList(null);

        Article targetArticle = getArticleById(articleId, allArticles);
        ArticleFeatures targetFeatures = extractFeatures(targetArticle);

        Map<Article, Double> similarityMap = new HashMap<>();
        for (Article article : allArticles) {
            if (!article.getId().equals(articleId)) {
                ArticleFeatures features = extractFeatures(article);
                double similarity = similarityCalculator.calculateSimilarity(targetFeatures, features);
                similarityMap.put(article, similarity);
            }
        }

        List<Map.Entry<Article, Double>> sortedSimilarities = new ArrayList<>(similarityMap.entrySet());
        sortedSimilarities.sort(Map.Entry.<Article, Double>comparingByValue().reversed());

        List<Article> recommendedArticles = new ArrayList<>();
        for (int i = 0; i < Math.min(numRecommendations, sortedSimilarities.size()); i++) {
            recommendedArticles.add(sortedSimilarities.get(i).getKey());
        }

        return recommendedArticles;
    }

    private Article getArticleById(Long articleId, List<Article> articles) {
        for (Article article : articles) {
            if (article.getId().equals(articleId)) {
                return article;
            }
        }
        return null;
    }

    private ArticleFeatures extractFeatures(Article article) {
        Long categoryId = article.getCategoryId();
        String title = article.getTitle();
        return new ArticleFeatures(article.getId(), categoryId, title);
    }
}
