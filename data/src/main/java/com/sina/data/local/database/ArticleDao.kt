package com.sina.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sina.data.local.entities.ArticleEntity
import com.sina.library.response.DataError
import com.sina.library.response.Result
import com.sina.library.response.safeLocalCall

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(article: ArticleEntity)

    @Query(
        """
    SELECT * FROM articles 
    WHERE title LIKE '%' || :query || '%' 
       OR description LIKE '%' || :query || '%' 
       OR content LIKE '%' || :query || '%' 
    ORDER BY publishedAt DESC
"""
    )
    suspend fun searchArticles(query: String): List<ArticleEntity>

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: String): ArticleEntity?
}

