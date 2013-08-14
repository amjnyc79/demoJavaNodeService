var http = require('http');

var server = http.createServer(function(req, res) {
  res.writeHead(200);
  
  res.end('Your node Service confirmed'+res.get('MESSAGE')+', responding with OK!');
});
server.listen(9080);