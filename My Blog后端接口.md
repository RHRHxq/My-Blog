---
title: 图书管理系统后端
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 图书管理系统后端

Base URLs:

# Authentication

# My Blog/用户

## POST 登录

POST /login

> Body 请求参数

```json
{
  "username": "fjq",
  "password": "1234"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": {
    "id": 0,
    "userName": "string",
    "token": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|object|true|none||none|
|»» id|integer|true|none||none|
|»» userName|string|true|none||none|
|»» token|string|true|none||none|

## POST 注册

POST /register

> Body 请求参数

```json
{
  "username": "ddd",
  "password": "1234"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 关注用户

POST /follow

> Body 请求参数

```json
{
  "userId": 4,
  "followedUserId": 6
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» userId|body|integer| 是 |none|
|» followedUserId|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获取用户的已经关注的人列表

GET /follow/followed/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "userId": 0,
      "followedUserId": 0,
      "createTime": [
        0
      ]
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» followedUserId|integer|false|none||none|
|»» createTime|[integer]|false|none||none|

## GET 获取用户的粉丝列表

GET /follow/follower/{followedUserId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|followedUserId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "userId": 0,
      "followedUserId": 0,
      "createTime": [
        0
      ]
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» followedUserId|integer|false|none||none|
|»» createTime|[integer]|false|none||none|

## POST 发送私信

POST /message

> Body 请求参数

```json
{
  "senderId": 5,
  "receiverId": 4,
  "content": "你好"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» senderId|body|integer| 是 |none|
|» receiverId|body|integer| 是 |none|
|» content|body|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获得聊天记录

GET /message/{senderId}/{receiverId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|senderId|path|integer| 是 |none|
|receiverId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "senderId": 0,
      "receiverId": 0,
      "content": "string",
      "createTime": [
        0
      ]
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» senderId|integer|false|none||none|
|»» receiverId|integer|false|none||none|
|»» content|string|false|none||none|
|»» createTime|[integer]|false|none||none|

# My Blog/后台页面/个人信息管理

## GET 消息通知

GET /SystemMessage/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# My Blog/后台页面/文章信息管理

## GET 文章筛选和多维度排序

GET /articles

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|categoryId|query|integer| 否 |分组|
|tagId|query|integer| 否 |none|
|publishTime|query|string| 否 |none|
|sortField|query|string| 否 |排序字段|
|sortOrder|query|string| 否 |排序顺序|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "title": "string",
      "summary": "string",
      "content": "string",
      "coverImage": "string",
      "categoryId": 0,
      "userId": 0,
      "status": 0,
      "publishTime": 0,
      "views": 0,
      "likes": 0,
      "comments": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» title|string|true|none||none|
|»» summary|string|true|none||none|
|»» content|string|true|none||none|
|»» coverImage|string|true|none||none|
|»» categoryId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» status|integer|true|none||none|
|»» publishTime|integer¦null|true|none||none|
|»» views|integer|true|none||none|
|»» likes|integer|true|none||none|
|»» comments|integer|true|none||none|

# My Blog/后台页面/文章管理/文章增删改查

## POST 添加文章

POST /article

> Body 请求参数

```json
{
  "id": 15,
  "title": "斗罗大陆15",
  "summary": "斗罗大陆",
  "content": "斗罗大陆",
  "coverImage": "www",
  "categoryId": 2
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» title|body|string| 是 |none|
|» summary|body|string| 是 |none|
|» content|body|string| 是 |none|
|» coverImage|body|string| 是 |none|
|» categoryId|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## DELETE 删除文章

DELETE /article/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## PUT 修改文章

PUT /article/{id}

> Body 请求参数

```json
{
  "title": "斗罗大陆2"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string| 是 |none|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» title|body|string| 是 |名称|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 查询文章

GET /article/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": {
    "id": 0,
    "title": "string",
    "summary": "string",
    "content": "string",
    "coverImage": "string",
    "categoryId": 0,
    "userId": 0,
    "status": 0,
    "publishTime": null,
    "views": 0,
    "likes": 0,
    "comments": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|object|true|none||none|
|»» id|integer|true|none||none|
|»» title|string|true|none||none|
|»» summary|string|true|none||none|
|»» content|string|true|none||none|
|»» coverImage|string|true|none||none|
|»» categoryId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» status|integer|true|none||none|
|»» publishTime|null|true|none||none|
|»» views|integer|true|none||none|
|»» likes|integer|true|none||none|
|»» comments|integer|true|none||none|

# My Blog/后台页面/文章管理/草稿箱功能

## GET 查询草稿

GET /draft

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "title": "string",
      "summary": "string",
      "content": "string",
      "coverImage": "string",
      "categoryId": 0,
      "userId": 0,
      "status": 0,
      "publishTime": null,
      "views": 0,
      "likes": 0,
      "comments": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» title|string|false|none||none|
|»» summary|string|false|none||none|
|»» content|string|false|none||none|
|»» coverImage|string|false|none||none|
|»» categoryId|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» status|integer|false|none||none|
|»» publishTime|null|false|none||none|
|»» views|integer|false|none||none|
|»» likes|integer|false|none||none|
|»» comments|integer|false|none||none|

## POST 添加草稿

POST /draft

> Body 请求参数

```json
{
  "id": 11,
  "title": "斗罗大陆7",
  "summary": "斗罗大陆",
  "content": "斗罗大陆",
  "coverImage": "www",
  "categoryId": 2
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

# My Blog/后台页面/文章管理/图片上传功能

## POST 图片上传回显

POST /upload

> Body 请求参数

```yaml
file: file://C:\Users\28648\Desktop\11d466fda502f65ff83fce0c1e4e98d7.png

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» file|body|string(binary)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

# My Blog/后台页面/文章管理/回收站功能

## DELETE 文章移入回收站

DELETE /article/recycle/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## PUT 从回收站恢复

PUT /article/recycle/restore/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## DELETE 回收站永久删除

DELETE /article/recycle/permanently/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获取回收站列表

GET /article/recycle

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "title": "string",
      "summary": "string",
      "content": "string",
      "coverImage": "string",
      "categoryId": 0,
      "userId": 0,
      "status": 0,
      "publishTime": null,
      "views": 0,
      "likes": 0,
      "comments": 0,
      "isDeleted": 0,
      "deleteTime": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» title|string|true|none||none|
|»» summary|string|true|none||none|
|»» content|string|true|none||none|
|»» coverImage|string|true|none||none|
|»» categoryId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» status|integer|true|none||none|
|»» publishTime|null|true|none||none|
|»» views|integer|true|none||none|
|»» likes|integer|true|none||none|
|»» comments|integer|true|none||none|
|»» isDeleted|integer|true|none||none|
|»» deleteTime|integer|true|none||none|

# My Blog/后台页面/文章管理/文章访问统计

## PUT 增加访问量

PUT /articles/{articleId}/views

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获取文章访问量

GET /articles/{articleId}/views/count

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|integer|true|none||none|

## PUT 点赞文章

PUT /articles/{articleId}/likes

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## PUT 取消点赞文章

PUT /articles/{articleId}/unlikes

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获取文章点赞量

GET /articles/{articleId}/likes/count

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": 0
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|integer|true|none||none|

# My Blog/后台页面/文章管理/专题聚合功能

## POST 创建合集

POST /topic

> Body 请求参数

```json
{
  "id": 1,
  "name": "玄幻",
  "description": "好",
  "status": 1,
  "userId": 4
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» id|body|integer| 是 |ID 编号|
|» name|body|string| 是 |none|
|» description|body|string| 是 |none|
|» status|body|integer| 是 |none|
|» userId|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|null|true|none||none|

## POST 添加文章进专题

POST /topic/link

> Body 请求参数

```json
{
  "articleId": 2,
  "topicId": 1
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» articleId|body|integer| 是 |none|
|» topicId|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|null|true|none||none|

## GET 获取该专题下的文章

GET /topic/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "title": "string",
      "summary": "string",
      "content": "string",
      "coverImage": "string",
      "categoryId": 0,
      "userId": 0,
      "status": 0,
      "publishTime": 0,
      "views": 0,
      "likes": 0,
      "comments": 0,
      "isDeleted": 0,
      "deleteTime": null
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|false|none||none|
|»» title|string|false|none||none|
|»» summary|string|false|none||none|
|»» content|string|false|none||none|
|»» coverImage|string|false|none||none|
|»» categoryId|integer|false|none||none|
|»» userId|integer|false|none||none|
|»» status|integer|false|none||none|
|»» publishTime|integer|false|none||none|
|»» views|integer|false|none||none|
|»» likes|integer|false|none||none|
|»» comments|integer|false|none||none|
|»» isDeleted|integer|false|none||none|
|»» deleteTime|null|false|none||none|

# My Blog/后台页面/文章管理/多级分类功能

## POST 新增分类

POST /categories

> Body 请求参数

```json
{
  "id": 10,
  "name": "springcloud",
  "parentId": 4,
  "sort": "1",
  "status": 1,
  "": "ex et fugiat"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» id|body|integer| 是 |ID 编号|
|» name|body|string| 是 |none|
|» parentId|body|integer| 是 |none|
|» sort|body|string| 是 |none|
|» status|body|integer| 是 |none|
|» *anonymous*|body|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 查询分类

GET /categories/{parentId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|parentId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "name": "string",
      "parentId": 0,
      "sort": 0,
      "status": 0,
      "createTime": [
        0
      ]
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» name|string|true|none||none|
|»» parentId|integer|true|none||none|
|»» sort|integer|true|none||none|
|»» status|integer|true|none||none|
|»» createTime|[integer]|true|none||none|

# My Blog/后台页面/文章管理/获取待审文章

## GET 获取待审文章列表

GET /pending

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 同意发布

POST /pending/approve/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## POST 审核拒绝

POST /pending/reject/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## POST 批量通过

POST /approve/batch

> Body 请求参数

```json
[
  19,
  20
]
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## POST 批量拒绝

POST /reject/batch

> Body 请求参数

```json
[
  23,
  24
]
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

# My Blog/后台页面/评论管理

## POST 添加评论

POST /comment

> Body 请求参数

```json
{
  "articleId": 1,
  "parentId": 1,
  "userId": 4,
  "content": "确实",
  "replyTo": "2"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» articleId|body|integer| 是 |none|
|» parrentId|body|integer| 是 |none|
|» userId|body|integer| 是 |none|
|» content|body|string| 是 |none|
|» replyTo|body|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 获得评论

GET /{articleId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|articleId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "articleId": 0,
      "parentId": 0,
      "userId": 0,
      "content": "string",
      "status": 0,
      "likes": 0,
      "createTime": [
        0
      ],
      "replyTo": 0
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» articleId|integer|true|none||none|
|»» parentId|integer¦null|true|none||none|
|»» userId|integer|true|none||none|
|»» content|string|true|none||none|
|»» status|integer|true|none||none|
|»» likes|integer|true|none||none|
|»» createTime|[integer]|true|none||none|
|»» replyTo|integer¦null|true|none||none|

## DELETE 删除评论

DELETE /comment/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## PUT 点赞评论

PUT /comments/{commentId}/likes

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|commentId|path|string| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## DELETE 取消点赞评论

DELETE /comments/{commentId}/unlikes

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|commentId|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## POST 上传举报

POST /comment/report

> Body 请求参数

```json
{
  "commentId": 1,
  "userId": 4,
  "reason": "你猜"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|
|body|body|object| 否 |none|
|» commentId|body|integer| 是 |none|
|» userId|body|integer| 是 |none|
|» reason|body|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

## GET 查看举报

GET /comment/report

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": [
    {
      "id": 0,
      "commentId": 0,
      "userId": 0,
      "reason": "string",
      "status": 0,
      "createTime": [
        0
      ]
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|[object]|true|none||none|
|»» id|integer|true|none||none|
|»» commentId|integer|true|none||none|
|»» userId|integer|true|none||none|
|»» reason|string|true|none||none|
|»» status|integer|true|none||none|
|»» createTime|[integer]|true|none||none|

## PUT 处理举报

PUT /comment/report/{id}/process

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|token|header|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": null,
  "data": "string"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|true|none||none|
|» msg|null|true|none||none|
|» data|string|true|none||none|

# 数据模型

