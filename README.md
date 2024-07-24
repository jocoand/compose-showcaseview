<p>
  <img src="https://img.shields.io/badge/version-1.1.0-blue.svg" alt="version">
  <img src="https://img.shields.io/badge/platform-android-brightgreen.svg" alt="platform">
</p>

## 🌟 Compose Showcase View
A simple showcase library for highlighting components and showing a dialog on it. 

<p>
  <img src="https://github.com/user-attachments/assets/db339610-3818-4d81-b44e-11ae8395df45" width="300" alt="preview">
</p>

For more pre-configured implementation see [Sequence Showcase](https://github.com/jocoand/compose-showcase?tab=readme-ov-file#-sequence-showcase)

### Installation
- Gradle
  ```
  implementation("io.github.jocoand:compose-showcaseview:1.1.0")
  ```

### Usage
- Create your Showcase dialog
- ```
    @Composable
    fun MyShowcaseDialog(text: String, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                Text("Nice !")
            }
        }
    }
  ```
  
- Get your target component `LayoutCoordinates` using `onGloballyPositioned` modifier
- ```
  val targets = remember { mutableStateMapOf<String, LayoutCoordinates>() }
  var greetingShowcaseVisible by remember { mutableStateOf(false) }
  
  ...
  // Target component to be highlighted
  Greeting(
      modifier = Modifier
          .align(Alignment.End)
          .onGloballyPositioned { coordinates ->
              targets[KEY_GREETING] = coordinates
          },
      name = "Andy",
      onClick = { greetingShowcaseVisible = true }
  )
  ```

- Declare `ShowcaseView`, use `visible` to set ShowcaseView visibility
- ```
  targets[KEY_SHARE]?.let { coordinates ->
      ShowcaseView(
          visible = visibleShowcase == VisibleShowcase.Share,
          targetCoordinates = coordinates,
      ) {
          MyShowcaseDialog(
              text = "Click to Share the article",
              onClick = { visibleShowcase = VisibleShowcase.None }
          )
      }
  }
  ```
  You may want to put you ShowcaseView in your top most componenent to make the overlay cover the whole screen
  ```
    Box(modifier = Modifier.fillMaxSize()) {
      Scaffold(
          ...
      ) { innerPadding ->
      }
        targets[KEY_GREETING]?.let { coordinates ->
            ShowcaseView(
                visible = greetingShowcaseVisible,
                targetCoordinates = coordinates,
            ) {
                MyShowcaseDialog(
                    text = "Hey, this is Greetings showcase",
                    onClick = { greetingShowcaseVisible = false }
                )
            }
        }
    }
  ```
### Config
- `position`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/1e368c82-b301-4c8a-95f2-d76562686d2b" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/d1a791b9-791e-40cd-aac9-6ff0452a7584" width="200">
      </td>

    </tr>
    <tr>
      <td align="center">Top</td>
      <td align="center">Bottom</td>
    </tr>
  </table>

  `Default`: relative to target position

- `alignment`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/bcc803b5-f570-43b7-bbfc-a00c3ae1ec5c" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/65695ac9-3fca-4b79-85b6-2d3d30e8d122" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/e4e5f045-cf92-4ef0-8570-a9410867ccbc" width="200">
      </td>
    </tr>
    <tr>
      <td align="center">Start</td>
      <td align="center">Center</td>
      <td align="center">End</td>
    </tr>
  </table>

  `Default`: relative to target position

### :beers: Contributing
- Contribution are welcome!
  Feel free to open an issue or a pull request, if you find any bugs or have any suggestions.

### 💡Inspired by
- https://github.com/SimformSolutionsPvtLtd/SSComposeShowCaseView

### 🎨 Sample
- [MainActivity](https://github.com/jocoand/compose-showcaseview/blob/main/app/src/main/java/com/joco/composeshowcaseview/MainActivity.kt)
