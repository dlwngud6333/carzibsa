module.exports = {
    entry: './src/main/web-app/js/index.js',

    output: {
        path: __dirname + '/src/main/web-app/',
        filename: 'bundle.js'
    },

    devServer: {
        inline: true,
        port: 7777,
        contentBase: __dirname + '/src/main/web-app/',
        historyApiFallback: true
    },
    module: {
            rules: [
                  {
                    test: /\.(js|jsx)$/,
                    exclude: /node_modules/,
                    use: {
                        loader: 'babel-loader',
                        query: {
                            cacheDirectory: true,
                            presets: ["@babel/react", "@babel/env"]
                        }
                    }
                  }, {
                    test: /\.css$/,
                    use: [{
                            loader: 'style-loader'
                        },
                        {
                            loader: 'css-loader'
                        }
                    ]
                },
                {
                    test: /\.(ttf|eot|svg|woff|woff2)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                    use: [{
                        loader: 'file-loader'
                    }]
                },
                {
                    test: /\.(png|jpg|gif)$/,
                    use: [{
                        loader: 'file-loader'
                    }]
                }
        
            ]
        }
};