const fs = require('fs');
const path = require('path');
const glob = require('glob');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const PurgecssPlugin = require('purgecss-webpack-plugin')

const ENTRY_POINT_PATHS = [
  './js/main.js',
  './css/main.scss'
].concat(fs.readdirSync(path.resolve(__dirname, 'images')).map(name => './images/' + name));

const PURGE_SCAN_PATHS = glob.sync(`${path.resolve(__dirname, '../resources/templates')}/**/*`, {nodir: true})
  .concat(glob.sync(`${path.resolve(__dirname, 'js')}/**/*`, {nodir: true}));

const config = {
  entry: ENTRY_POINT_PATHS,
  output: {
    path: path.resolve(__dirname, 'build'),
  },
  plugins: [
    new MiniCssExtractPlugin(),
  ],
  module: {
    rules: [
      {
        test: /\.(sa|sc|c)ss$/i,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          {
            loader: 'postcss-loader',
            options: {
              postcssOptions: {
                sourceMap: true,
                plugins: [
                  require('autoprefixer')
                ]
              }
            }
          },
          'sass-loader'
        ],
      },
      {
        test: /\.svg$/i,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: 'images/[name].[ext]'
            },
          },
          {
            loader: 'svgo-loader',
            options: {
              multipass: true,
              plugins: [{
                name: 'preset-default',
                params: {overrides: {cleanupIDs: {remove: false}}},
              }],
            }
          }
        ]
      },
    ],
  },
  devServer: {
    port: 3000,
    setupMiddlewares: (middlewares, devServer) => {
      devServer.app.use('/', (req, res, next) => {
        console.log(`[Assets] - [${req.ip}] - ${req.method} ${req.originalUrl}`);
        next();
      });
      return middlewares;
    }
  }
};

module.exports = (env, argv) => {
  if (argv.mode === 'development') {
    config.devtool = 'source-map';
  }

  if (argv.mode === 'production') {
    config.plugins.push(new PurgecssPlugin({
      paths: PURGE_SCAN_PATHS,
    }));
  }

  return config;
};
