import { Client } from "@notionhq/client";
import { NotionToMarkdown } from "notion-to-md";
import fs from "fs";

const notion = new Client({
  auth: process.env.NOTION_TOKEN,
});
const n2m = new NotionToMarkdown({ notionClient: notion });
(async () => {
  const mdblocks = await n2m.pageToMarkdown("efce8b9534384ee495211f6dd531fb17");
  const mdString = n2m.toMarkdownString(mdblocks);
  fs.writeFileSync("./temp-data.md", mdString.parent);
})();
