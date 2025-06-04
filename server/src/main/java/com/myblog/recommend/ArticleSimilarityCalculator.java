package com.myblog.recommend;

import com.myblog.entity.ArticleFeatures;
import org.apache.commons.text.similarity.LevenshteinDistance;

//相似度计算类
public class ArticleSimilarityCalculator {
    public double calculateSimilarity(ArticleFeatures article1, ArticleFeatures article2) {
        double similarity = 0;

        // 如果文章分类相同，增加相似度
        if (article1.getCategoryId().equals(article2.getCategoryId())) {
            similarity += 0.5;
        }

        // 计算标题相似度
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(article1.getTitle(), article2.getTitle());
        //使用**莱文斯坦距离（Levenshtein Distance）**计算两个标题的编辑距离 distance；
        //用该距离除以两标题中较长的长度，得到归一化的差异比例；
        //用 1 - 差异比例 得到标题的相似度 titleSimilarity，值越小表示越相似
        double titleSimilarity = 1 - (double) distance / Math.max(article1.getTitle().length(), article2.getTitle().length());
        similarity += titleSimilarity * 0.5;

        return similarity;
    }
}
