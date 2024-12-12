import { APP_BASE_HREF } from '@angular/common';
import { CommonEngine } from '@angular/ssr';
import express from 'express';
import { fileURLToPath } from 'node:url';
import { dirname, join, resolve } from 'node:path';
import bootstrap from './src/main.server';

// The Express app is exported so that it can be used by serverless Functions.
export function app(): express.Express {
  const server = express();
  const serverDistFolder = dirname(fileURLToPath(import.meta.url));
  const browserDistFolder = resolve(serverDistFolder, '../dist/app'); // Adjusted path for Angular build output
  const indexHtml = join(browserDistFolder, 'index.html'); // Using index.html instead of index.server.html

  const commonEngine = new CommonEngine();

  server.set('view engine', 'html');
  server.set('views', browserDistFolder);

  // Serve static files from /dist/app
  server.get('*.*', express.static(browserDistFolder, {
    maxAge: '1y',
    index: false, // Prevent auto-serving index.html for static files
  }));

  // All regular routes use the Angular engine
  server.get('*', (req, res, next) => {
    const { protocol, originalUrl, baseUrl, headers } = req;

    commonEngine
        .render({
          bootstrap,
          documentFilePath: indexHtml,
          url: `${protocol}://${headers.host}${originalUrl}`,
          publicPath: browserDistFolder,
          providers: [{ provide: APP_BASE_HREF, useValue: baseUrl }],
        })
        .then((html) => res.send(html))
        .catch((err) => next(err));
  });

  return server;
}

function run(): void {
  const port = process.env['PORT'] || 4000; // Fixed TS4111 error by using ['PORT']

  // Start up the Node server
  const server = app();
  server.listen(port, () => {
    console.log(`Node Express server listening on http://localhost:${port}`);
  });
}

run();
