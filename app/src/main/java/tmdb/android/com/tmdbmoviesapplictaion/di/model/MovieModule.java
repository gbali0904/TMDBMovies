/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tmdb.android.com.tmdbmoviesapplictaion.di.model;


import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import tmdb.android.com.tmdbmoviesapplictaion.data.APIServices;
import tmdb.android.com.tmdbmoviesapplictaion.di.scope.PerActivity;
import tmdb.android.com.tmdbmoviesapplictaion.main.view.MoviesListView;


@Module
public class MovieModule {

    private MoviesListView mView;

    public MovieModule(MoviesListView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    APIServices provideApiService(Retrofit retrofit) {
        return retrofit.create(APIServices.class);
    }

    @PerActivity
    @Provides
    MoviesListView provideView() {
        return mView;
    }
}
