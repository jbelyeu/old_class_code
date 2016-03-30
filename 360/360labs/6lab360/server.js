var fs = require('fs');
var http = require('http');
var url = require('url');
var ROOT_DIR = "html/";
http.createServer(function (req, res) 
{
	var urlObj = url.parse(req.url, true, false);

	if (urlObj.pathname.indexOf("getcity") != -1)
	{
		var queryStr = urlObj.query['q'];
		var queryRe = new RegExp("^" + queryStr, 'i');
		fs.readFile(ROOT_DIR + "cities.txt", function (err, data)
		{
			if (err)
			{
				res.writeHead(404);
				res.end(JSON.stringify(err));
				return;
			}
			var cities = data.toString().split("\n");
			var matches = [];
			
			for (var i = 0, len = cities.length; i < len; ++i)
			{
				var found = cities[i].search(queryRe);
				if (found != -1)
				{
					matches.push({city: cities[i]});
				}
			}
			res.writeHead(200);
			res.end(JSON.stringify(matches));
		});
	}
	else
	{
		fs.readFile(ROOT_DIR + urlObj.pathname, function (err,data) 
		{
			if (err) 
			{
				res.writeHead(404);
				res.end(JSON.stringify(err));
				return;
			}
			res.writeHead(200);
			res.end(data);
		});
	}
}).listen(80);

