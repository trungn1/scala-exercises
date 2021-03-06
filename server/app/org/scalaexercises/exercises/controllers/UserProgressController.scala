/*
 *  scala-exercises
 *
 *  Copyright 2015-2017 47 Degrees, LLC. <http://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.scalaexercises.exercises.controllers

import org.scalaexercises.algebra.app._
import org.scalaexercises.algebra.user.UserOps
import org.scalaexercises.algebra.progress.{UserExercisesProgress, UserProgressOps}
import org.scalaexercises.algebra.exercises.ExerciseOps
import org.scalaexercises.exercises.services.interpreters.ProdInterpreters
import doobie.imports._
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.Controller

import scalaz.concurrent.Task
import scala.concurrent.ExecutionContext.Implicits.global
import freestyle._
import freestyle.implicits._

class UserProgressController(
    implicit exerciseOps: ExerciseOps[ExercisesApp.Op],
    userOps: UserOps[ExercisesApp.Op],
    exercisesOps: ExerciseOps[ExercisesApp.Op],
    userExercisesProgress: UserExercisesProgress[ExercisesApp.Op],
    T: Transactor[Task]
) extends Controller
    with JsonFormats
    with AuthenticationModule
    with ProdInterpreters {

  def fetchUserProgressBySection(libraryName: String, sectionName: String) =
    AuthenticatedUser { user ⇒
      userExercisesProgress.fetchUserProgressByLibrarySection(user, libraryName, sectionName) map {
        response ⇒
          Ok(Json.toJson(response))
      }
    }
}
