<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en-gb"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en-gb"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en-gb"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-gb"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Kickstrap</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
  <!-- You can also compile style.less to use regular css. Your apps will still work. -->
  <style type="text/css">body{padding-top:60px}.hidden{margin:20px;border:5px solid #a24c4c;background-color:red;padding:10px;width:400px;color:white;font-family:helvetica,sans-serif}</style>
  <link rel="stylesheet/less" type="text/css" href="/bookstore-example-with-mvc/resources/css/kickstrap.less">
  <script src="/bookstore-example-with-mvc/resources/css/Kickstrap/js/less-1.3.0.min.js"></script>
</head>
<body>
<div id="sf-wrapper"> <!-- Sticky Footer Wrapper -->
   <div class="hidden"><h1>No Stylesheet Loaded</h1><p><strong>Could not load Kickstrap.</strong>There are <a href="http://getkickstrap.com/docs/1.1/#lessjs-errors">several common reasons for this error.</a></p></div>
  <!-- Prompt IE 6/7 users to install Chrome Frame. Remove this if you support IE 7-.
       chromium.org/developers/how-tos/chrome-frame-getting-started -->
  <!--[if lt IE 8]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->
<!--! END KICKSTRAP HEADER --> 

        <tiles:insertAttribute name="header"/>
    
    	<div id="qunit"></div>
    
    <div class="container">
    	<div class="row">
    	  <div class="span12">
    	  
            <tiles:insertAttribute name="body" />

    	  </div>
    	</div>
    </div>  	

  <!--! KICKSTRAP FOOTER -->
  <div id="push"></div></div> <!-- sf-wrapper -->

       <tiles:insertAttribute name="footer" />       

  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="Kickstrap/js/jquery-1.7.1.min.js"><\/script>');</script>
  <!-- Kickstrap CDN thanks to our friends at netDNA.com -->
  <script id="appList" src="http://netdna.getkickstrap.com/Kickstrap/js/kickstrap.min.js"></script>
  <script>window.consoleLog || document.write('<script id="appList" src="Kickstrap/js/kickstrap.min.js"><\/script>')</script>
  <script>
   kickstrap.ready(function() {
      // JavaScript placed here will run only once Kickstrap has loaded successfully.
      $.pnotify({
         title: 'Hello World',
         text: 'To edit this message, find me at the bottom of this HTML file.'
      });
   });
  </script>
  <!-- Asynchronous Google Analytics snippet. Change UA-XXXXX-X to be your site's ID.
       mathiasbynens.be/notes/async-analytics-snippet -->
  <!--script>
    var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
    (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
    g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
    s.parentNode.insertBefore(g,s)}(document,'script'));
  </script-->
</body>
</html>


            