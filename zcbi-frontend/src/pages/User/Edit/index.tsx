import { genChartByAiUsingPOST } from '@/services/zcbi/chartController';
import {CheckOutlined, UploadOutlined} from '@ant-design/icons';
import {Avatar, Button, Card, Col, Divider, Form, Input, message, Modal, Row, Select, Space, Spin, Upload,} from 'antd';
import TextArea from 'antd/es/input/TextArea';
import ReactECharts from 'echarts-for-react';
import React, { useState } from 'react';
import {Edit} from "@sinclair/typebox/value";
import { UserOutlined } from '@ant-design/icons';
import {useModel} from "@@/exports";

/**
 * 用户编辑页面（用户个人端）
 * @constructor
 */
const UserProfileEdit: React.FC = () => {
  // 定义状态，用来接收后端的返回值，让它实时展示在页面上
  const [chart, setChart] = useState<API.BiResponse>();
  const [option, setOption] = useState<any>();
  // 提交中的状态，默认未提交
  const [submitting, setSubmitting] = useState<boolean>(false);

  // 从全局状态中获取到当前登录的用户信息
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};

  const [points, setPoints] = useState(10); // 初始积分
  const [rechargeAmount, setRechargeAmount] = useState(''); // 充值积分数量
  const [visible, setVisible] = useState(false); // 充值模态框可见状态
  const [isSignedIn, setIsSignedIn] = useState(false);


  /**
   * 提交
   * @param values
   */
  const onFinish = async (values: any) => {
    console.log('Received values of form: ', values);
    // 这里可以添加将表单数据提交到服务器的逻辑

  };

  const onAvatarChange = (info: any) => {
    if (info.file.status === 'done') {
      message.success('头像上传成功');
      // 可以在这里更新头像的URL或其他操作
    } else if (info.file.status === 'error') {
      message.error('头像上传失败');
    }
  };

  const handleSignIn = () => {
    if (!isSignedIn) {
      setPoints(points + 10); // 假设签到成功后积分增加 10
      setIsSignedIn(true);
      message.success('签到成功，积分增加 10！');
    } else {
      message.warning('您今天已经签过到了！');
    }
  };

  const handleRecharge = () => {
    if (!rechargeAmount) {
      message.error('请输入充值积分数量');
      return;
    }
    // 这里可以处理积分充值逻辑，如发送请求等
    setPoints(points + parseInt(rechargeAmount));
    setRechargeAmount('');
    setVisible(false);
  };

  return (
    // 把页面内容指定一个类名称 user-profile-edit
    <div className="user-profile-edit">
      <div style={{ padding: '20px' }}>
        <Card title="个人信息">
          <Form
            name="userProfile"
            onFinish={onFinish}
            initialValues={{
              username: currentUser?.userName || "",

              // 其他字段
            }}
          >
            {/* 头像上传 */}
            <Form.Item label="头像">

              <Upload
                name="avatar"
                action="/api/upload"  // 替换为您的文件上传接口
                onChange={onAvatarChange}
                showUploadList={false}
              >
                <Avatar size={64} icon={<UserOutlined />} src={currentUser && currentUser.userAvatar} />
              </Upload>
            </Form.Item>

            <Form.Item
              label="用户名"
              name="username"
              rules={[
                {
                  required: true,
                  message: '请输入用户名',
                },
              ]}
            >
              <Input />
            </Form.Item>

            {/* 其他字段的 Form.Item */}

            <Form.Item>
              <Button type="primary" htmlType="submit">
                保存
              </Button>
            </Form.Item>
          </Form>
        </Card>

        {/* 用户积分 */}
        <Card title="我的积分" style={{ marginTop: '20px' }}>
          <p>您当前的积分：{points}</p>
          <Button
            type="primary"
            onClick={handleSignIn}
            icon={<CheckOutlined />}
            disabled={isSignedIn}
          >
            签到
          </Button>
        </Card>

        {/* 充值积分 */}
        <Card title="充值积分" style={{ marginTop: '20px' }}>
          <Button type="primary" onClick={() => setVisible(true)}>充值</Button>
          <Modal
            title="充值积分"
            visible={visible}
            onCancel={() => setVisible(false)}
            onOk={handleRecharge}
          >
            <Form layout="vertical">
              <Form.Item label="充值积分数量(1积分=1￥)">
                <Input
                  value={rechargeAmount}
                  onChange={(e) => setRechargeAmount(e.target.value)}
                  placeholder="请输入充值积分数量"
                  type="number"
                />
              </Form.Item>
            </Form>
          </Modal>
        </Card>
      </div>
    </div>
  );
};
export default UserProfileEdit;
