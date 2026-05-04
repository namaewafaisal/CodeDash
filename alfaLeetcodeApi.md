
## Endpoints 🚀

### 👤User Details

| Details                       | Endpoint                             | Description                                                          | Demo                                                              |
| :---------------------------- | :----------------------------------- | :------------------------------------------------------------------- | ----------------------------------------------------------------- |
| _Profile_                     | `/:username`                         | Get details about a user's profile.                                  | <a href="./public/demo/demo2.png" target="_blank">click here</a>  |
| _Full Profile_                | `/:username/profile`                 | Get full profile details in one call                                 | <a href="./public/demo/demo22.png" target="_blank">click here</a> |
| _Badges_                      | `/:username/badges`                  | Get the badges earned by the user.                                   | <a href="./public/demo/demo3.png" target="_blank">click here</a>  |
| _Solved_                      | `/:username/solved`                  | Get the total number of questions solved by the user.                | <a href="./public/demo/demo4.png" target="_blank">click here</a>  |
| _Contest_                     | `/:username/contest`                 | Get details about the user's contest participation.                  | <a href="./public/demo/demo5.png" target="_blank">click here</a>  |
| _Contest History_             | `/:username/contest/history`         | Get all contest history.                                             | <a href="./public/demo/demo6.png" target="_blank">click here</a>  |
| _Submission_                  | `/:username/submission`              | Get the last 20 submissions of the user.                             | <a href="./public/demo/demo7.png" target="_blank">click here</a>  |
| _Limited Submission_          | `/:username/submission?limit=number` | Get a specified **_number_** of the user's last submissions.         | <a href="./public/demo/demo8.png" target="_blank">click here</a>  |
| _Accepted Submission_         | `/:username/acSubmission`            | Get the last 20 accepted submission of the user.                     | <a href="./public/demo/demo16.png" target="_blank">click here</a> |
| _Limited Accepted Submission_ | `/:username/acSubmission?limit=7`    | Get a specified **_number_** of the user's last accepted submission. | <a href="./public/demo/demo17.png" target="_blank">click here</a> |
| _Calendar_                    | `/:username/calendar`                | Get the user's submission calendar.                                  | <a href="./public/demo/demo9.png" target="_blank">click here</a>  |
| _Calendar with year_          | `/:username/calendar?year=2025`      | Get the user's submission calendar with `year` query                 | <a href="./public/demo/demo23.png" target="_blank">click here</a> |
| _Skill Stats_                 | `/:username/skill`                   | Get the user's skill stats.                                          | <a href="./public/demo/demo24.png" target="_blank">click here</a> |
| _Lang Stats_                  | `/:username/language`                | Get the language stats of a user                                     | <a href="./public/demo/demo25.png" target="_blank">click here</a> |
| _Question Progress_           | `/:username/progress`                | Get your question progress                                           | <a href="./public/demo/demo26.png" target="_blank">click here</a> |

### ❓Questions Details

| Details                            | Endpoint                                                 | Description                                                                                                                  | Demo                                                              |
| :--------------------------------- | :------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| _Daily Problem_                    | `/daily`                                                 | Get the daily question.                                                                                                      | <a href="./public/demo/demo10.png" target="_blank">click here</a> |
| _Daily Problem Raw_                | `/daily/raw`                                             | Get the daily question in raw.                                                                                               | <a href="./public/demo/demo27.png" target="_blank">click here</a> |
| _Selected Problem_                 | `/select?titleSlug=selected-question`                    | Get details about a **_selected-question_**.                                                                                 | <a href="./public/demo/demo11.png" target="_blank">click here</a> |
| _Raw Selected Problem_             | `/select/raw?titleSlug=selected-question`                | Get raw selected question                                                                                                    | <a href="./public/demo/demo28.png" target="_blank">click here</a> |
| _Problems_                         | `/problems`                                              | Get a list of 20 problems.                                                                                                   | <a href="./public/demo/demo12.png" target="_blank">click here</a> |
| _Limited Problems_                 | `/problems?limit=number`                                 | Get a list of a specified **_number_** of problems.                                                                          | <a href="./public/demo/demo13.png" target="_blank">click here</a> |
| _Filter Problems_                  | `/problems?tags=tag1+tag2`                               | Get a list of problems based on selected **_tags_**.                                                                         | <a href="./public/demo/demo14.png" target="_blank">click here</a> |
| _Skip Problems_                    | `/problems?skip=number`                                  | Get a list of 20 problems, skipping a specified **_number_** of problems.                                                    | <a href="./public/demo/demo18.png" target="_blank">click here</a> |
| _Difficulty_                       | `/problems?difficulty=EASY`                              | Get a list of difficulty based problems, use **_MEDIUM_** to get medium level, **_HARD_** to get Hard level .                | <a href="./public/demo/demo19.png" target="_blank">click here</a> |
| _Filter & Limited Problems_        | `/problems?tags=tag1+tag2+tag3&limit=number`             | Get a list of a specified **_number_** of problems based on selected **_tags_**.                                             | <a href="./public/demo/demo15.png" target="_blank">click here</a> |
| _Skip & Limited Problems_          | `/problems?limit=number&skip=number`                     | Get a list of a specified **_number_** of problems skipping a specified **number** of problems.                              | <a href="./public/demo/demo20.png" target="_blank">click here</a> |
| _Skip & Filter & Limited Problems_ | `/problems?tags=tag1+tag2+tag3&limit=number&skip=number` | Get a list of a specified **_number_** of problems based on selected **_tags_** skipping a specified **number** of problems. | <a href="./public/demo/demo21.png" target="_blank">click here</a> |
| _Official Solution_                | `/officialSolution?titleSlug=selected-question`          | Get the official solution(leetcode) for a question                                                                       | <a href="./public/demo/demo29.png" target="_blank">click here</a> |

### 🏆 Contests

| Details             | Endpoint             | Description                | Demo                                                              |
| :------------------ | :------------------- | :------------------------- | ----------------------------------------------------------------- |
| _All Contests_      | `/contests`          | Get all the contests       | <a href="./public/demo/demo30.png" target="_blank">click here</a> |
| _Upcoming Contests_ | `/contests/upcoming` | Get the upcoming contests. | <a href="./public/demo/demo31.png" target="_blank">click here</a> |

### 🗪 Discussion

| Details               | Endpoint                    | Description                     | Demo                                                             |
| :-------------------- | :-------------------------- | :------------------------------ | ---------------------------------------------------------------- |
| _Trending Discussion_ | `/trendingDiscuss?first=20` | Get top 20 trending discussions | <a href="./public/demo/demo32.png" target="_blank">click here</a> |
| _Discussion Topic_    | `/discussTopic/:topicId`    | Get discussion topic            | <a href="./public/demo/demo33.png" target="_blank">click here</a> |
| _Discussion Comment_  | `/discussComments/:topicId` | Get discussion comments         | <a href="./public/demo/demo34.png" target="_blank">click here</a> |