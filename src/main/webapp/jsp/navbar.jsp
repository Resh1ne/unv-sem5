<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Navbar</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>

<!-- Горизонтальная навигационная панель с кнопками -->
<div class="navbar">
  <button class="navbar_button" onclick="navigateTo('http://localhost:8080')">Home</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=exhibition_halls')">All exhibition halls</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=exhibitions')">All exhibitions</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=cur_exhibitions')">Current exhibitions</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=owners')">All owners</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=artists')">All artists</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=artworks')">All artworks</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=create_exhibition_hall')">Create exhibition hall</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=create_exhibition')">Create exhibition</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=create_owner')">Create owner</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=create_artist')">Create artist</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=create_artwork')">Create artwork</button>
</div>

<script>
  function navigateTo(url) {
    window.location.href = url;
  }
</script>