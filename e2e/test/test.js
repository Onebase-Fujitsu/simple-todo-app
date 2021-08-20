import { Selector } from 'testcafe';

fixture `test target`
    .page `http://localhost:3000`;

test('タスク一覧画面を見ることができる', async t => {
    await t
        .expect(Selector('body').withText('Simple TodoList Application').exists).ok()
        .expect(Selector('input#taskNameInput').exists).ok()
        .expect(Selector('input#taskNameInput').value).eql('')
        .expect(Selector('input#taskDescriptionInput').exists).ok()
        .expect(Selector('input#taskDescriptionInput').value).eql('')
        .expect(Selector('button').withText("SAVE").exists).ok()
})

test('タスクを作成することができる', async t => {
    await t
        .typeText(Selector('input#taskNameInput'), 'title', {replace: true})
        .expect(Selector('input#taskNameInput').value).eql('title')
        .typeText(Selector('input#taskDescriptionInput'), 'description', {replace: true})
        .expect(Selector('input#taskDescriptionInput').value).eql('description')
        .click(Selector('button').withText("SAVE"))
        .expect(Selector('ul').child().nth(-1).find('h3').withText('title').exists).ok()
        .expect(Selector('ul').child().nth(-1).find('p').withText('description').exists).ok()
        .expect(Selector('ul').child().nth(-1).find('button').exists).ok()
})

test('タスク完了することができる', async t => {
    await t
        .click(Selector('ul').child().nth(-1))
        .expect(Selector('ul').child().nth(-1).find('h3').withAttribute('style', 'text-decoration: line-through;').exists).ok()
        .expect(Selector('ul').child().nth(-1).find('p').withAttribute('style', 'text-decoration: line-through;').exists).ok()
})

test('タスクを未完了に戻すことができる', async t => {
    await t
        .click(Selector('ul').child().nth(-1))
        .expect(Selector('ul').child().nth(-1).find('h3').withAttribute('style', 'text-decoration: none;').exists).ok()
        .expect(Selector('ul').child().nth(-1).find('p').withAttribute('style', 'text-decoration: none;').exists).ok()
})

test('タスクを削除することができる', async t => {
    await t
        .click(Selector('ul').child().nth(-1).find('button'))
        .expect(Selector('ul').child().nth(-1).find('h3').withText('title').exists).notOk()
        .expect(Selector('ul').child().nth(-1).find('p').withText('description').exists).notOk()
})