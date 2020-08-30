package com.project.apptest.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.apptest.model.data.Post
import com.project.apptest.model.data.StateBackButton
import com.project.apptest.model.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var post: Post = Post()
    private var listPosts: LinkedList<Post> = LinkedList()
    private var it = listPosts.listIterator()
    private var stateBackButton = MutableLiveData<StateBackButton>()

    private fun loadNextGif() {
        runBlocking {
            post =
                withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                    PostRepository().getPost()
                }
        }
    }

    fun getFirstPost(): Post {
        loadNextGif()
        stateBackButton.value = StateBackButton(false)
        listPosts.add(0, post)
        return post
    }

    fun getNextPost(): Post {
        it = listPosts.listIterator(listPosts.indexOf(post) + 1)
        stateBackButton.value = StateBackButton(true)
        if (it.hasNext()) {
            post = it.next()
            return post
        } else {
            loadNextGif()
            listPosts.add(post)
            return post
        }
    }

    fun getPrevPost(): Post {
        it = listPosts.listIterator(listPosts.indexOf(post))
        if (it.hasPrevious()) {
            post = it.previous()
            if (listPosts[0] == post) {
                stateBackButton.value = StateBackButton(false)
            }
            return post
        } else {
            return Post()
        }
    }

    fun changeStateBackButton(): MutableLiveData<StateBackButton> {
        return stateBackButton
    }
}